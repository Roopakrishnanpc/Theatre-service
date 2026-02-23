package com.movieticket.theatre.infrastructure.read.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theatre")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreReadEntity {

    @Id
    private Long id;

    private String name;
    private String city;

    @Column(name="partner_username")
    private String partnerUsername;

    private boolean active;
}