package com.umutavci.imdb.infrastructure.persistence.repositories;

import com.umutavci.imdb.infrastructure.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
