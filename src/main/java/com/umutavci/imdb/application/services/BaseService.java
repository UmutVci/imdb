package com.umutavci.imdb.application.services;

import com.umutavci.imdb.domain.models.Base;
import com.umutavci.imdb.domain.ports.repositories.IBaseRepository;

import java.util.List;

public interface BaseService <INPUT, OUTPUT extends Base>{

    OUTPUT getSingle(Long id);
    List<OUTPUT> getAll();
    OUTPUT create(INPUT input);
    OUTPUT update(Long id, INPUT input);
    boolean delete(Long id);

}
