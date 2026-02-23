package com.movieticket.theatre.web.command;

import com.movieticket.theatre.application.command.ShowUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/v1/tickets/command/shows")
@RequiredArgsConstructor
public class ShowCommandController {

    private static final Logger log =
            LoggerFactory.getLogger(ShowCommandController.class);

    private final ShowUseCase showUseCase;

    // Only PARTNER or ADMIN
    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long theatreId,
                                    @RequestParam String movieName,
                                    @RequestParam String language,
                                    @RequestParam String genre,
                                    @RequestParam LocalDate showDate,
                                    @RequestParam LocalTime showTime,
                                    Authentication authentication) {

        log.info("HTTP Request -> Create Show: theatreId={}, movie={}, user={}",
                theatreId, movieName, authentication.getName());

        showUseCase.createShow(
                theatreId,
                movieName,
                language,
                genre,
                showDate,
                showTime,
                authentication.getName()
        );

        return ResponseEntity
                .status(201)
                .body("Show created successfully");
    }

    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    Authentication authentication) {

        log.info("HTTP Request -> Delete Show id={}, user={}",
                id, authentication.getName());

        showUseCase.deleteShow(id, authentication.getName());

        return ResponseEntity
                .ok("Show deactivated successfully");
    }
}