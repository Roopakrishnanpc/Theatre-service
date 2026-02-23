package com.movieticket.theatre.domain.exception;

public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException(String message) {
        super(message);
    }
}