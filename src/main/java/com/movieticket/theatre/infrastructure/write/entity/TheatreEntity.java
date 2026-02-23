package com.movieticket.theatre.infrastructure.write.entity;

import com.movieticket.theatre.domain.model.Theatre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "theatre")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;

    @Column(name = "partner_username")
    private String partnerUsername;

    private boolean active;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static TheatreEntity fromDomain(Theatre t) {
        return TheatreEntity.builder()
                .id(t.getId())
                .name(t.getName())
                .city(t.getCity())
                .partnerUsername(t.getPartnerUsername())
                .active(t.isActive())
                .version(t.getVersion())
                .deletedAt(t.getDeletedAt())
                .build();
    }

    public Theatre toDomain() {
        return new Theatre(
                id,
                name,
                city,
                partnerUsername,
                active,
                version,
                deletedAt
        );
    }
}