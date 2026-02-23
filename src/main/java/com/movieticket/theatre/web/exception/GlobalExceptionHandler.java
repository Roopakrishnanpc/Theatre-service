package com.movieticket.theatre.web.exception;

import com.movieticket.theatre.domain.exception.*;

import jakarta.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TheatreNotFoundException.class)
    public ResponseEntity<?> handleTheatreNotFound(TheatreNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(ShowNotFoundException.class)
    public ResponseEntity<?> handleShowNotFound(ShowNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedPartnerException.class)
    public ResponseEntity<?> handleUnauthorized(UnauthorizedPartnerException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error("FORBIDDEN", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<?> handleDataIntegrity(DataIntegrityException ex) {

        log.error("Data integrity issue", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error("DATA_INTEGRITY_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {

        log.error("Unexpected error", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error("INTERNAL_ERROR", "Something went wrong"));
    }

    private Map<String, Object> error(String code, String message) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "code", code,
                "message", message
        );
    }
    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<?> handleOptimisticLock() {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "code", "CONFLICT",
                        "message", "Resource was modified by another user"
                ));
    }
    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<String> handleForbidden(ForbiddenOperationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }
}