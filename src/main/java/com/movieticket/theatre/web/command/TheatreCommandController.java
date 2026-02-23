package com.movieticket.theatre.web.command;

import com.movieticket.theatre.application.command.TheatreUseCase;
import com.movieticket.theatre.domain.model.Theatre;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets/command/theatres")
@RequiredArgsConstructor
public class TheatreCommandController {

    private static final Logger log =
            LoggerFactory.getLogger(TheatreCommandController.class);

    private final TheatreUseCase theatreUseCase;

    // PARTNER and ADMIN can create theatre
    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestParam String name,
                                    @RequestParam String city,
                                    Authentication authentication) {

        log.info("HTTP Request -> Create Theatre: name={}, city={}, user={}",
                name, city, authentication.getName());

        Theatre theatre = theatreUseCase.createTheatre(
                name,
                city,
                authentication.getName()
        );

        return ResponseEntity
                .status(201)
                .body("Theatre created with id: " + theatre.getId());
    }

    // PARTNER and ADMIN can delete
    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    Authentication authentication) {

        log.info("HTTP Request -> Delete Theatre id={}, user={}",
                id, authentication.getName());

        theatreUseCase.deleteTheatre(id, authentication.getName());

        return ResponseEntity
                .ok("Theatre deactivated successfully");
    }
}