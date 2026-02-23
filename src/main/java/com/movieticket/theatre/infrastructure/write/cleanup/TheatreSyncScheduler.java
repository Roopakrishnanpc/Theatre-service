package com.movieticket.theatre.infrastructure.write.cleanup;

import com.movieticket.theatre.infrastructure.write.entity.TheatreEntity;
import com.movieticket.theatre.infrastructure.write.repository.TheatreJpaRepository;
import com.movieticket.theatre.infrastructure.read.entity.TheatreReadEntity;
import com.movieticket.theatre.infrastructure.read.repository.TheatreReadRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TheatreSyncScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(TheatreSyncScheduler.class);

    private final TheatreJpaRepository writeRepository;
    private final TheatreReadRepository readRepository;

    // Every 10 seconds
    @Scheduled(fixedDelay = 10000)
    @Transactional("writeTransactionManager")
    public void syncToReadDb() {

        log.info("Starting theatre read-model sync");

        List<TheatreEntity> theatres =
                writeRepository.findAll();

        for (TheatreEntity t : theatres) {

            TheatreReadEntity read =
                    TheatreReadEntity.builder()
                            .id(t.getId())
                            .name(t.getName())
                            .city(t.getCity())
                            .partnerUsername(t.getPartnerUsername())
                            .active(t.isActive())
                            .build();

            readRepository.save(read);
        }

        log.info("Theatre read-model sync completed");
    }
}