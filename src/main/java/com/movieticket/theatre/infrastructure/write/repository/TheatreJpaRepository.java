package com.movieticket.theatre.infrastructure.write.repository;

import com.movieticket.theatre.infrastructure.write.entity.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TheatreJpaRepository
        extends JpaRepository<TheatreEntity, Long> {

    Optional<TheatreEntity>
    findByIdAndPartnerUsernameAndActiveTrue(Long id, String partner);
    @Modifying
    @Query("""
       DELETE FROM TheatreEntity t
       WHERE t.active = false
       AND t.deletedAt < :cutoff
    """)
    void deleteInactiveOlderThan(LocalDateTime cutoff);
}