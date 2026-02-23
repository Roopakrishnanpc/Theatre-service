package com.movieticket.theatre.application.query;

import com.movieticket.theatre.application.query.dto.ShowBrowseDTO;
import com.movieticket.theatre.infrastructure.read.entity.ShowReadEntity;
import com.movieticket.theatre.infrastructure.read.entity.TheatreReadEntity;
import com.movieticket.theatre.infrastructure.read.repository.ShowReadRepository;
import com.movieticket.theatre.infrastructure.read.repository.TheatreReadRepository;
import com.movieticket.theatre.web.dto.TheatreResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreQueryUseCaseImpl implements TheatreQueryUseCase {

    private static final Logger log =
            LoggerFactory.getLogger(TheatreQueryUseCaseImpl.class);

    private final ShowReadRepository showReadRepository;
    private final TheatreReadRepository theatreReadRepository;

    @Override
    @Transactional(readOnly = true, transactionManager = "readTransactionManager")
    @Cacheable(
            value = "browseCache",
            key = "#city + '_' + #movie + '_' + #date"
    )
    public List<ShowBrowseDTO> browse(String city,
                                      String movie,
                                      LocalDate date) {

        log.info("Browse request: city={}, movie={}, date={}",
                city, movie, date);

        List<ShowBrowseDTO> result =
                showReadRepository.browse(city, movie, date);

        log.info("Browse result count={}", result.size());

        return result;
    }

    @Override
    @Transactional(readOnly = true, transactionManager = "readTransactionManager")
    @Cacheable(value = "theatreByCityCache", key = "#city")
    public List<TheatreResponseDTO> findByCity(String city) {

        log.info("Fetching theatres by city={}", city);

        List<TheatreResponseDTO> result =
                theatreReadRepository.findByCityAndActiveTrue(city)
                        .stream()
                        .map(entity ->
                                TheatreResponseDTO.builder()
                                        .id(entity.getId())
                                        .name(entity.getName())
                                        .city(entity.getCity())
                                        .build()
                        )
                        .toList();

        log.info("Theatre fetch result count={}", result.size());

        return result;
    }
    
    @Override
    @Cacheable(value = "showByIdCache", key = "#showId")
    public ShowBrowseDTO getShowById(Long showId) {

        ShowReadEntity show = showReadRepository
                .findByIdAndActiveTrue(showId)
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));

        TheatreReadEntity theatre = theatreReadRepository
                .findById(show.getTheatreId())
                .orElseThrow(() ->
                        new RuntimeException("Theatre not found"));

        return new ShowBrowseDTO(
                show.getId(),
                theatre.getName(),
                show.getMovieName(),
                show.getLanguage(),
                show.getGenre(),
                show.getShowDate(),
                show.getShowTime(),
                show.isActive()
        );
    }
}