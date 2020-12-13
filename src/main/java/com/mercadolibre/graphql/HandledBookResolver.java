package com.mercadolibre.graphql;

import graphql.ExceptionWhileDataFetching;
import graphql.execution.DataFetcherResult;
import graphql.execution.ExecutionPath;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.language.SourceLocation;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class HandledBookResolver implements GraphQLResolver<Book> {

    private AuthorRepository authorRepository;

    public HandledBookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public CompletableFuture<DataFetcherResult<Author>> author(Book book, DataFetchingEnvironment environment) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return DataFetcherResult.<Author>newResult().data(authorRepository.findById(book.getAuthorId())).build();
            } catch (Exception t) {
                final ExecutionPath path = environment.getExecutionStepInfo().getPath();
                final SourceLocation sourceLocation = environment.getField().getSourceLocation();
                return DataFetcherResult.<Author>newResult().error(new ExceptionWhileDataFetching(path, t, sourceLocation)).build();
            }
        });
    }
}
