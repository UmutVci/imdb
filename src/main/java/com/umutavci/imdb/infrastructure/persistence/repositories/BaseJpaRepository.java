package com.umutavci.imdb.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseJpaRepository<T> extends JpaRepository<T, Long> {
}
