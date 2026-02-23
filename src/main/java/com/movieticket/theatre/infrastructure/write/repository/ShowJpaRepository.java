package com.movieticket.theatre.infrastructure.write.repository;

import com.movieticket.theatre.infrastructure.write.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowJpaRepository
        extends JpaRepository<ShowEntity, Long> {
}