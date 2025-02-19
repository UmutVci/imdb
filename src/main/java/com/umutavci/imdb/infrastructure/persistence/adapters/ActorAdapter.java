package com.umutavci.imdb.infrastructure.persistence.adapters;

import com.umutavci.imdb.domain.models.in.ActorInput;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Actor;
import com.umutavci.imdb.infrastructure.persistence.mapper.ActorMapper;
import com.umutavci.imdb.infrastructure.persistence.repositories.ActorJpaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActorAdapter implements ICRUDAdapter<ActorInput, ActorResponse>{
    @Autowired
    private final ActorJpaRespository actorJpaRespository;
    @Autowired
    private final ActorMapper actorMapper;

    public ActorAdapter(ActorJpaRespository actorJpaRespository, ActorMapper actorMapper) {
        this.actorJpaRespository = actorJpaRespository;
        this.actorMapper = actorMapper;
    }

    @Override
    public ActorResponse getSingle(Long id) {
        Actor actor = actorJpaRespository.findById(id).orElseThrow( () -> new RuntimeException("Actor not found"));
        return actorMapper.toActorResponse(actor);
    }

    @Override
    public List<ActorResponse> getAll() {
        return actorJpaRespository.findAll()
                .stream()
                .map(actorMapper::toActorResponse)
                .toList();
    }

    @Override
    public ActorResponse create(ActorInput actorInput) {
        Actor actor = actorMapper.toActor(actorInput);
        actor.setCreatedAt(LocalDateTime.now());
        actor.setUpdatedAt(LocalDateTime.now());
        Actor savedActor = actorJpaRespository.save(actor);
        return actorMapper.toActorResponse(savedActor);
    }

    @Override
    public ActorResponse update(Long id, ActorInput actorInput) {
        Actor actor = actorJpaRespository.findById(id).orElseThrow( () -> new RuntimeException("Actor not found"));
        actor.setBirthDate(actorInput.getBirthDate());
        actor.setName(actorInput.getName());
        actor.setUpdatedAt(LocalDateTime.now());
        Actor savedActor = actorJpaRespository.save(actor);
        return actorMapper.toActorResponse(savedActor);
    }

    @Override
    public boolean delete(Long id) {
        Actor actor = actorJpaRespository.findById(id).orElseThrow( () -> new RuntimeException("Actor not found"));
        actorJpaRespository.delete(actor);
        return !actorJpaRespository.findById(id).isPresent();
    }
}
