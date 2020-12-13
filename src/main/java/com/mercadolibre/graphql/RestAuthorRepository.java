package com.mercadolibre.graphql;

public class RestAuthorRepository implements AuthorRepository {
    @Override
    public Author findById(int authorId) {
        return Author.builder().id(1).name("Ernesto Sábato").build();
    }
}
