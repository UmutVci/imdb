package com.umutavci.imdb.infrastructure.persistence.repositories;

import com.umutavci.imdb.infrastructure.persistence.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorJpaRepository extends JpaRepository<Director, Long> {
}
