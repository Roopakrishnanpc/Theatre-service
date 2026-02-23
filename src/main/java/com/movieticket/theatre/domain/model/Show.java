package com.movieticket.theatre.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@Builder
public class Show {

    private Long id;
    private Long theatreId;
    private String movieName;
    private String language;
    private String genre;
    private LocalDate showDate;
    private LocalTime showTime;
    private boolean active;
    private Long version;

    public void deactivate() {
        this.active = false;
    }
}