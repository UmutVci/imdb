package com.umutavci.imdb.infrastructure.persistence.repositories;

import com.umutavci.imdb.infrastructure.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = :email")
    User findByEmail(String email);
}
