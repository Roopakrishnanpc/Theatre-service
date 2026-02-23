package com.movieticket.theatre.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Theatre {

    private Long id;
    private String name;
    private String city;
    private String partnerUsername;
    private boolean active;
    private Long version;
    private LocalDateTime deletedAt;

    public void deactivate() {
        this.active = false;
        this.deletedAt = LocalDateTime.now();
    }
}