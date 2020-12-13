package com.mercadolibre.graphql;

import java.util.List;

public class RestBookRepository implements BookRepository {
    @Override
    public List<Book> findAll() {
        return List.of(Book.builder().id(1).authorId(1).name("El Túnel").build());
    }
}
