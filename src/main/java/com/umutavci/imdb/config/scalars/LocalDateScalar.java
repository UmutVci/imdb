package com.umutavci.imdb.config.scalars;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateScalar {
    public static final GraphQLScalarType LOCAL_DATE = GraphQLScalarType.newScalar()
            .name("LocalDate")
            .description("Java LocalDate")
            .coercing(new Coercing<LocalDate, String>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

                @Override
                public String serialize(Object dataFetcherResult) {
                    if (dataFetcherResult instanceof LocalDate) {
                        return ((LocalDate) dataFetcherResult).format(formatter);
                    }
                    throw new IllegalArgumentException("");
                }

                @Override
                public LocalDate parseValue(Object input) {
                    if (input instanceof String) {
                        return LocalDate.parse((String) input, formatter);
                    }
                    throw new IllegalArgumentException("");
                }

                @Override
                public LocalDate parseLiteral(Object input) {
                    if (input instanceof String) {
                        return LocalDate.parse((String) input, formatter);
                    }
                    throw new IllegalArgumentException("");
                }
            })
            .build();
}