package com.umutavci.imdb.domain.ports.repositories;

import com.umutavci.imdb.domain.models.in.UserInput;
import com.umutavci.imdb.domain.models.out.UserResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserRepository extends IBaseRepository<UserInput, UserResponse>{
}
