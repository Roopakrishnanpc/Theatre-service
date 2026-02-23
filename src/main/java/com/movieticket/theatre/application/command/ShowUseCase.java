package com.movieticket.theatre.application.command;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ShowUseCase {

    void createShow(Long theatreId,
                    String movieName,
                    String language,
                    String genre,
                    LocalDate showDate,
                    LocalTime showTime,
                    String partnerUsername);

    void deleteShow(Long showId,
                    String partnerUsername);
}