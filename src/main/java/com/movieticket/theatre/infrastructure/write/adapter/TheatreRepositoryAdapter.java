package com.movieticket.theatre.infrastructure.write.adapter;

import com.movieticket.theatre.domain.model.Theatre;
import com.movieticket.theatre.domain.repository.TheatreRepository;
import com.movieticket.theatre.infrastructure.write.entity.TheatreEntity;
import com.movieticket.theatre.infrastructure.write.repository.TheatreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TheatreRepositoryAdapter implements TheatreRepository {

    private final TheatreJpaRepository jpa;

    @Override
    public Theatre save(Theatre theatre) {

        TheatreEntity entity = TheatreEntity.fromDomain(theatre);

        TheatreEntity saved = jpa.save(entity);

        // Important: return updated entity (version increment handled by JPA)
        return saved.toDomain();
    }

    @Override
    public Optional<Theatre> findById(Long id) {
        return jpa.findById(id)
                .filter(TheatreEntity::isActive)   // ignore soft deleted
                .map(TheatreEntity::toDomain);
    }

    @Override
    public Optional<Theatre> findByIdAndPartner(Long id,
                                               String partner) {

        return jpa.findByIdAndPartnerUsernameAndActiveTrue(id, partner)
                .map(TheatreEntity::toDomain);
    }
}