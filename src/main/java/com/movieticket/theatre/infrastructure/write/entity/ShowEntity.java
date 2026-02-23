package com.movieticket.theatre.infrastructure.write.entity;

import com.movieticket.theatre.domain.model.Show;
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
public class ShowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "theatre_id")
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

    @Version
    @Column(nullable = false)
    private Long version;

    public Show toDomain() {
        return Show.builder()
                .id(id)
                .theatreId(theatreId)
                .movieName(movieName)
                .language(language)
                .genre(genre)
                .showDate(showDate)
                .showTime(showTime)
                .active(active)
                .version(version)
                .build();
    }

    public static ShowEntity fromDomain(Show show) {
        return ShowEntity.builder()
                .id(show.getId())
                .theatreId(show.getTheatreId())
                .movieName(show.getMovieName())
                .language(show.getLanguage())
                .genre(show.getGenre())
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .active(show.isActive())
                .version(show.getVersion())
                .build();
    }
}