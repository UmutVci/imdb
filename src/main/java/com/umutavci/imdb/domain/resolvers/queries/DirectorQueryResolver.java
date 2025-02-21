package com.umutavci.imdb.domain.resolvers.queries;

import com.umutavci.imdb.application.services.DirectorService;
import com.umutavci.imdb.domain.models.out.DirectorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DirectorQueryResolver {
    @Autowired
    private final DirectorService directorService;

    public DirectorQueryResolver(DirectorService directorService) {
        this.directorService = directorService;
    }

    @QueryMapping
    public DirectorResponse getDirector(@Argument Long id){
        return directorService.getSingle(id);
    }
    @QueryMapping
    public List<DirectorResponse> getAllDirectors(){
        return directorService.getAll();
    }
}
