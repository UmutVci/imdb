package com.umutavci.imdb.application.services;

import com.umutavci.imdb.domain.models.Base;
import com.umutavci.imdb.domain.ports.repositories.IBaseRepository;

import java.util.List;

public abstract class BaseService <INPUT, OUTPUT extends Base>{
    private final IBaseRepository<INPUT, OUTPUT> repository;

    protected BaseService(IBaseRepository<INPUT, OUTPUT> repository) {
        this.repository = repository;
    }

    OUTPUT getSingle(Long id){ return repository.getSingle(id); }
    List<OUTPUT> getAll(){ return repository.getAll(); }
    OUTPUT create(INPUT input){ return repository.create(input); }
    OUTPUT update(Long id, INPUT input){ return repository.update(id, input); }
    boolean delete(Long id){ return repository.delete(id); }

}
