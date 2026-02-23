package com.movieticket.theatre.domain.exception;

public class UnauthorizedPartnerException extends RuntimeException {
    public UnauthorizedPartnerException() {
        super("You are not authorized to modify this theatre");
    }
}