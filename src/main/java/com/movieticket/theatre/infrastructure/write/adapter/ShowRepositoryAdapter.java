package com.movieticket.theatre.infrastructure.write.adapter;

import com.movieticket.theatre.domain.model.Show;
import com.movieticket.theatre.domain.repository.ShowRepository;
import com.movieticket.theatre.infrastructure.write.entity.ShowEntity;
import com.movieticket.theatre.infrastructure.write.repository.ShowJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShowRepositoryAdapter implements ShowRepository {

    private final ShowJpaRepository jpa;

    @Override
    public Show save(Show show) {

        ShowEntity entity = ShowEntity.fromDomain(show);
        ShowEntity saved = jpa.save(entity);

        return saved.toDomain();
    }

    @Override
    public Optional<Show> findById(Long id) {

        return jpa.findById(id)
                .map(ShowEntity::toDomain);
    }
}