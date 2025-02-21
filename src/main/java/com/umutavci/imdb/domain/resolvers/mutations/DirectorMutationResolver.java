package com.umutavci.imdb.domain.resolvers.mutations;

import com.umutavci.imdb.application.services.DirectorService;
import com.umutavci.imdb.domain.models.in.DirectorInput;
import com.umutavci.imdb.domain.models.out.DirectorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class DirectorMutationResolver {
    @Autowired
    private final DirectorService directorService;
    public DirectorMutationResolver(DirectorService directorService) {
        this.directorService = directorService;
    }
    @MutationMapping
    public DirectorResponse createDirector(@Argument DirectorInput input){
        return directorService.create(input);
    }
    @MutationMapping
    public boolean deleteDirector(@Argument Long id){
        return directorService.delete(id);
    }
    @MutationMapping
    public DirectorResponse updateDirector(@Argument Long id, @Argument DirectorInput input){
        return directorService.update(id, input);
    }
}
