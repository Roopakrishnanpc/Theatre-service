package com.movieticket.theatre.application.query.dto;

import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ShowBrowseDTO {

    private Long id;
    private String theatreName;
    private String movieName;
    private String language;
    private String genre;
    private LocalDate showDate;
    private LocalTime showTime;
    private boolean active;
    // 🔥 THIS IS REQUIRED FOR JPQL
    public ShowBrowseDTO(Long id,
                         String theatreName,
                         String movieName,
                         String language,
                         String genre,
                         LocalDate showDate,
                         LocalTime showTime,
                         boolean active) {
        this.id = id;
        this.theatreName = theatreName;
        this.movieName = movieName;
        this.language = language;
        this.genre = genre;
        this.showDate = showDate;
        this.showTime = showTime;
        this.active=active;
    }
}