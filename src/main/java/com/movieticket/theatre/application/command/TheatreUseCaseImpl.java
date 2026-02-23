package com.movieticket.theatre.application.command;

import com.movieticket.theatre.domain.exception.ForbiddenOperationException;
import com.movieticket.theatre.domain.exception.TheatreNotFoundException;
import com.movieticket.theatre.domain.exception.UnauthorizedPartnerException;
import com.movieticket.theatre.domain.model.Theatre;
import com.movieticket.theatre.domain.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TheatreUseCaseImpl implements TheatreUseCase {

    private static final Logger log =
            LoggerFactory.getLogger(TheatreUseCaseImpl.class);

    private final TheatreRepository theatreRepository;
    @Override
    @CacheEvict(value = {"theatreByCityCache", "browseCache"}, allEntries = true)
    @Transactional("writeTransactionManager")
    public Theatre createTheatre(String name,
                                 String city,
                                 String requestedOwner) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();
        String role = auth.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        log.info("Create theatre request: name={}, city={}, user={}, role={}",
                name, city, username, role);

        if ("ROLE_CUSTOMER".equals(role)) {
            throw new ForbiddenOperationException(
                    "Customers are not allowed to create theatres"
            );
        }

        String finalOwner;

        if ("ROLE_PARTNER".equals(role)) {
            // Partner can only create for himself
            finalOwner = username;
        } else {
            // ADMIN
            finalOwner = requestedOwner != null ? requestedOwner : username;
        }

        Theatre theatre = Theatre.builder()
                .id(null)
                .name(name)
                .city(city)
                .partnerUsername(finalOwner)
                .active(true)
                .version(0L)
                .deletedAt(null)
                .build();

        Theatre saved = theatreRepository.save(theatre);

        log.info("Theatre created successfully id={} owner={}",
                saved.getId(), finalOwner);

        return saved;
    }

    @Override
    @CacheEvict(value = {"theatreByCityCache", "browseCache"}, allEntries = true)
    @Transactional("writeTransactionManager")
    public void deleteTheatre(Long theatreId,
                              String username) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String role = auth.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        log.info("Delete theatre request id={}, user={}, role={}",
                theatreId, username, role);

        // CUSTOMER → completely blocked
        if ("ROLE_CUSTOMER".equalsIgnoreCase(role)) {
            throw new ForbiddenOperationException(
                    "Customers are not allowed to delete theatres"
            );
        }

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new TheatreNotFoundException(theatreId));

        // PARTNER → must own theatre
        if ("ROLE_PARTNER".equalsIgnoreCase(role)
                && !theatre.getPartnerUsername().equals(username)) {

            throw new UnauthorizedPartnerException();
        }

        // ADMIN → no ownership check

        theatre.deactivate();
        theatreRepository.save(theatre);

        log.info("Theatre deleted successfully id={}", theatreId);
    }
    
    
}