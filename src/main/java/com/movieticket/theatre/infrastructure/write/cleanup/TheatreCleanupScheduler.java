package com.movieticket.theatre.infrastructure.write.cleanup;

import com.movieticket.theatre.infrastructure.write.repository.TheatreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TheatreCleanupScheduler {

    private final TheatreJpaRepository repository;

    // Runs every day at 3 AM
    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanup() {

        LocalDateTime cutoff =
                LocalDateTime.now().minusDays(30);

        repository.deleteInactiveOlderThan(cutoff);
    }
    
}