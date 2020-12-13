package com.mercadolibre.graphql;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class BookResolver implements GraphQLResolver<Book> /* This class is a resolver for the Book "Data Class" */ {

    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public CompletableFuture<Author> author(Book book) {
        return CompletableFuture.supplyAsync(() -> authorRepository.findById(book.getAuthorId()));
    }

}