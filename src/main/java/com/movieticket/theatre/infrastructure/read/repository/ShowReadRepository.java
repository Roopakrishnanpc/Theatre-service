package com.movieticket.theatre.infrastructure.read.repository;

import com.movieticket.theatre.application.query.dto.ShowBrowseDTO;
import com.movieticket.theatre.infrastructure.read.entity.ShowReadEntity;
import com.movieticket.theatre.infrastructure.read.entity.TheatreReadEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ShowReadRepository
	        extends JpaRepository<ShowReadEntity, Long> {

	    @Query("""
	           SELECT new com.movieticket.theatre.application.query.dto.ShowBrowseDTO(
	               s.id,
	               t.name,
	               s.movieName,
	               s.language,
	               s.genre,
	               s.showDate,
	               s.showTime,
	               s.active
	           )
	           FROM ShowReadEntity s
	           JOIN TheatreReadEntity t ON s.theatreId = t.id
	           WHERE t.city = :city
	             AND s.movieName = :movie
	             AND s.showDate = :date
	             AND s.active = true
	             AND t.active = true
	           """)
	    List<ShowBrowseDTO> browse(
	            @Param("city") String city,
	            @Param("movie") String movie,
	            @Param("date") LocalDate date
	    );
	    Optional<ShowReadEntity> findByIdAndActiveTrue(Long id);
}