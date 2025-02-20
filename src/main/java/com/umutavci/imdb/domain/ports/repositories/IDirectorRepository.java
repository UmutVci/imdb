package com.umutavci.imdb.domain.ports.repositories;

import com.umutavci.imdb.domain.models.in.DirectorInput;
import com.umutavci.imdb.domain.models.out.DirectorResponse;

public interface IDirectorRepository extends IBaseRepository<DirectorInput, DirectorResponse>{
}
