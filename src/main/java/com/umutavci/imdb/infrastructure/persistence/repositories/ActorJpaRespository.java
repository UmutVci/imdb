package com.umutavci.imdb.infrastructure.persistence.repositories;

import com.umutavci.imdb.infrastructure.persistence.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorJpaRespository extends JpaRepository<Actor, Long> {
}
