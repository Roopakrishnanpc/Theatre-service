package com.movieticket.theatre.domain.repository;

import com.movieticket.theatre.domain.model.Show;

import java.util.Optional;

public interface ShowRepository {

    Show save(Show show);

    Optional<Show> findById(Long id);
}