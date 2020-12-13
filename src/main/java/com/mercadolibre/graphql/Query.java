package com.mercadolibre.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Query implements GraphQLQueryResolver {

    private final BookRepository bookRepository;

    public Query(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public CompletableFuture<List<Book>> books() {
        return CompletableFuture.supplyAsync(() -> bookRepository.findAll());
    }

}