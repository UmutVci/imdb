package com.umutavci.imdb.config;

import com.umutavci.imdb.config.scalars.LocalDateScalar;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {
    @Bean
    public GraphQLScalarType localDateScalar() {
            return LocalDateScalar.LOCAL_DATE;
    }
}
