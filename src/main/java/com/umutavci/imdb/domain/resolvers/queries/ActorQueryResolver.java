package com.umutavci.imdb.domain.resolvers.queries;

import com.umutavci.imdb.application.services.ActorService;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ActorQueryResolver {
    @Autowired
    private final ActorService actorService;
    public ActorQueryResolver(ActorService actorService) {
        this.actorService = actorService;
    }
    @QueryMapping
    public ActorResponse getActor(@Argument Long id){
        return actorService.getSingle(id);
    }
    @QueryMapping
    public List<ActorResponse> getAllActors(){
        return actorService.getAll();
    }
}
