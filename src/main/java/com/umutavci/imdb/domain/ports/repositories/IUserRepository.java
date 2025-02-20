package com.umutavci.imdb.domain.ports.repositories;

import com.umutavci.imdb.domain.models.in.UserInput;
import com.umutavci.imdb.domain.models.out.UserResponse;

public interface IUserRepository extends IBaseRepository<UserInput, UserResponse>{
}
