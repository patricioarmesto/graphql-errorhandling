package com.mercadolibre.graphql.mock;

import com.mercadolibre.graphql.Author;
import com.mercadolibre.graphql.AuthorRepository;

public class MockedWithErrorAuthorRepository implements AuthorRepository {

    @Override
    public Author findById(int authorId) {
        throw new IllegalStateException("Boom");
    }

}
