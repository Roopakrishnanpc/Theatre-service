package com.movieticket.theatre.infrastructure.write.cleanup;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.movieticket.theatre.infrastructure.read.entity.ShowReadEntity;
import com.movieticket.theatre.infrastructure.read.repository.ShowReadRepository;
import com.movieticket.theatre.infrastructure.write.entity.ShowEntity;
import com.movieticket.theatre.infrastructure.write.repository.ShowJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShowSyncScheduler {

    private final ShowJpaRepository writeRepository;
    private final ShowReadRepository readRepository;

    @Scheduled(fixedDelay = 10000)
    @Transactional("writeTransactionManager")
    public void syncShows() {

        List<ShowEntity> shows = writeRepository.findAll();

        shows.forEach(s -> {

            ShowReadEntity read = ShowReadEntity.builder()
                    .id(s.getId())
                    .theatreId(s.getTheatreId())
                    .movieName(s.getMovieName())
                    .language(s.getLanguage())
                    .genre(s.getGenre())
                    .showDate(s.getShowDate())
                    .showTime(s.getShowTime())
                    .active(s.isActive())
                    .build();

            readRepository.save(read);
        });
    }
}
