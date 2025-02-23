package com.umutavci.imdb.application.services;

import com.umutavci.imdb.domain.models.in.ActorInput;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import com.umutavci.imdb.domain.ports.repositories.IActorRepository;
import com.umutavci.imdb.domain.ports.repositories.IBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class ActorService implements BaseService<ActorInput, ActorResponse>{
    @Autowired
    private final IActorRepository repository;
    public ActorService(IActorRepository repository) {
        this.repository = repository;
    }
    @Override
    public ActorResponse getSingle(Long id){ return repository.getSingle(id); }
    @Override
    public List<ActorResponse> getAll(){ return repository.getAll(); }
    @Override
    public ActorResponse create(ActorInput input){ return repository.create(input); }
    @Override
    public ActorResponse update(Long id, ActorInput input){ return repository.update(id, input); }
    @Override
    public boolean delete(Long id){ return repository.delete(id); }
}
