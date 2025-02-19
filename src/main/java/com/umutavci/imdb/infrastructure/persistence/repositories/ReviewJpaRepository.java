package com.umutavci.imdb.infrastructure.persistence.repositories;

import com.umutavci.imdb.infrastructure.persistence.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
