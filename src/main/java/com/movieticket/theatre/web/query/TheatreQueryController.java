package com.movieticket.theatre.web.query;

import com.movieticket.theatre.application.query.TheatreQueryUseCase;
import com.movieticket.theatre.application.query.dto.ShowBrowseDTO;
import com.movieticket.theatre.web.dto.TheatreResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets/query")
@RequiredArgsConstructor
public class TheatreQueryController {

    private static final Logger log =
            LoggerFactory.getLogger(TheatreQueryController.class);

    private final TheatreQueryUseCase useCase;

    @GetMapping("/theatres")
    public ResponseEntity<List<TheatreResponseDTO>> getTheatres(
            @RequestParam String city) {

        log.info("HTTP Request -> Get theatres by city={}", city);

        List<TheatreResponseDTO> result =
                useCase.findByCity(city);

        return ResponseEntity.ok(result);
    }

	@GetMapping("/shows")
	public ResponseEntity<List<ShowBrowseDTO>> browse(
	        @RequestParam String city,
	        @RequestParam String movie,
	        @RequestParam String date) {
	
	    log.info("HTTP Request -> Browse: city={}, movie={}, date={}",
	            city, movie, date);
	
	    List<ShowBrowseDTO> result =
	            useCase.browse(city, movie, LocalDate.parse(date));
	
	    return ResponseEntity.ok(result);
	}
	
	@GetMapping("/show/{id}")
	public ResponseEntity<ShowBrowseDTO> getShowById(
	        @PathVariable Long id) {

	    log.info("HTTP Request -> Get Show by id={}", id);

	    ShowBrowseDTO result = useCase.getShowById(id);

	    return ResponseEntity.ok(result);
	}
}