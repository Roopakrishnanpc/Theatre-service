package com.movieticket.theatre.web.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreResponseDTO {

    private Long id;
    private String name;
    private String city;
}