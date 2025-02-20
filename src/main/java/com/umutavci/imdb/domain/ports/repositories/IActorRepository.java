package com.umutavci.imdb.domain.ports.repositories;

import com.umutavci.imdb.domain.models.in.ActorInput;
import com.umutavci.imdb.domain.models.out.ActorResponse;

public interface IActorRepository extends IBaseRepository<ActorInput, ActorResponse> {
}
