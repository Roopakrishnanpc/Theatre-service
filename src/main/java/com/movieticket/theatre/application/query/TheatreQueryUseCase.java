package com.movieticket.theatre.application.query;

import com.movieticket.theatre.application.query.dto.ShowBrowseDTO;
import com.movieticket.theatre.web.dto.TheatreResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface TheatreQueryUseCase {

    List<ShowBrowseDTO> browse(String city,
                               String movie,
                               LocalDate date);
    
    List<TheatreResponseDTO> findByCity(String city);

	ShowBrowseDTO getShowById(Long showId);
}