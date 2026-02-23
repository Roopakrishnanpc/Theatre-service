package com.movieticket.theatre.domain.repository;

import com.movieticket.theatre.domain.model.Theatre;

import java.util.Optional;

public interface TheatreRepository {

    Theatre save(Theatre theatre);

    Optional<Theatre> findById(Long id);

    Optional<Theatre> findByIdAndPartner(Long id, String partner);
}