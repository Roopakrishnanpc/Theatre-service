package com.movieticket.theatre.infrastructure.read.repository;

import com.movieticket.theatre.infrastructure.read.entity.TheatreReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreReadRepository
        extends JpaRepository<TheatreReadEntity, Long> {

    List<TheatreReadEntity> findByCityAndActiveTrue(String city);
}