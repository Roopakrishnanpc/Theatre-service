package com.movieticket.theatre.application.command;

import com.movieticket.theatre.domain.model.Show;
import com.movieticket.theatre.domain.model.Theatre;
import com.movieticket.theatre.domain.repository.ShowRepository;
import com.movieticket.theatre.domain.repository.TheatreRepository;
import com.movieticket.theatre.domain.exception.ForbiddenOperationException;
import com.movieticket.theatre.domain.exception.ShowNotFoundException;
import com.movieticket.theatre.domain.exception.TheatreNotFoundException;
import com.movieticket.theatre.domain.exception.UnauthorizedPartnerException;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ShowUseCaseImpl implements ShowUseCase {

    private static final Logger log =
            LoggerFactory.getLogger(ShowUseCaseImpl.class);

    private final ShowRepository showRepository;
    private final TheatreRepository theatreRepository;

    @Override
    @Transactional("writeTransactionManager")
    @CacheEvict(value = {"browseCache"}, allEntries = true)
    public void createShow(Long theatreId,
                           String movieName,
                           String language,
                           String genre,
                           LocalDate showDate,
                           LocalTime showTime,
                           String username) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String role = auth.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        log.info("Create show request theatreId={}, user={}, role={}",
                theatreId, username, role);

        if ("ROLE_CUSTOMER".equalsIgnoreCase(role)) {
            throw new ForbiddenOperationException(
                    "Customers are not allowed to create shows"
            );
        }

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new TheatreNotFoundException(theatreId));

        if ("ROLE_PARTNER".equalsIgnoreCase(role)
                && !theatre.getPartnerUsername().equals(username)) {

            throw new UnauthorizedPartnerException();
        }

        Show show = Show.builder()
                .theatreId(theatreId)
                .movieName(movieName)
                .language(language)
                .genre(genre)
                .showDate(showDate)
                .showTime(showTime)
                .active(true)
                .version(0L)
                .build();

        showRepository.save(show);

        log.info("Show created successfully theatreId={}", theatreId);
    }


    @Override
    @CacheEvict(value = {"browseCache", "showByIdCache"}, allEntries = true)
    @Transactional("writeTransactionManager")
    public void deleteShow(Long showId,
                           String username) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String role = auth.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        log.info("Delete show request id={}, user={}, role={}",
                showId, username, role);

        if ("ROLE_CUSTOMER".equalsIgnoreCase(role)) {
            throw new ForbiddenOperationException(
                    "Customers are not allowed to delete shows"
            );
        }

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ShowNotFoundException(showId));

        // If partner, verify ownership via theatre
        if ("ROLE_PARTNER".equalsIgnoreCase(role)) {

            Theatre theatre = theatreRepository
                    .findById(show.getTheatreId())
                    .orElseThrow(() ->
                            new TheatreNotFoundException(show.getTheatreId()));

            if (!theatre.getPartnerUsername().equals(username)) {
                throw new UnauthorizedPartnerException();
            }
        }

        // ADMIN skips ownership check

        show.deactivate();
        showRepository.save(show);

        log.info("Show deleted successfully id={}", showId);
    }
}