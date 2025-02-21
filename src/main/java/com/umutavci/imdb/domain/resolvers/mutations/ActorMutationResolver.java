package com.umutavci.imdb.domain.resolvers.mutations;

import com.umutavci.imdb.application.services.ActorService;
import com.umutavci.imdb.domain.models.in.ActorInput;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ActorMutationResolver {
    @Autowired
    private final ActorService actorService;
    public ActorMutationResolver(ActorService actorService) {
        this.actorService = actorService;
    }

    @MutationMapping
    public ActorResponse createActor(@Argument ActorInput input){
        return actorService.create(input);
    }
    @MutationMapping
    public boolean deleteActor(@Argument Long id){
        return actorService.delete(id);
    }
    @MutationMapping
    public ActorResponse updateActor(@Argument Long id, @Argument ActorInput input){
        return actorService.update(id, input);
    }
}
