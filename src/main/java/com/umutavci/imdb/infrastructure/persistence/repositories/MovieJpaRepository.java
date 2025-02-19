package com.umutavci.imdb.infrastructure.persistence.repositories;

import com.umutavci.imdb.infrastructure.persistence.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJpaRepository extends JpaRepository<Movie, Long> {
}
