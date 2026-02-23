package com.movieticket.theatre.infrastructure.read.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "theatre_show")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowReadEntity {

    @Id
    private Long id;

    @Column(name="theatre_id")
    private Long theatreId;
    @Column(name = "movie_name")
    private String movieName;
    private String language;
    private String genre;
    @Column(name="show_date")
    private LocalDate showDate;
    @Column(name="show_time")
    private LocalTime showTime;

    private boolean active;
}