package com.movieticket.theatre.application.command;

import com.movieticket.theatre.domain.model.Theatre;

public interface TheatreUseCase {

    Theatre createTheatre(String name,
                          String city,
                          String partner);

    void deleteTheatre(Long theatreId,
                       String partner);
}