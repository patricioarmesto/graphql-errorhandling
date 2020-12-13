package com.mercadolibre.graphql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mercadolibre.graphql.mock.MockedWithErrorAuthorRepository;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class TestErrorHandling {

    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Test
    public void testSuccessfulExecution() throws JsonProcessingException {

        AuthorRepository authorRepository = new RestAuthorRepository();
        BookRepository bookRepository = new RestBookRepository();

        final GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("my-schema.graphqls")
                .resolvers(new Query(bookRepository), new BookResolver(authorRepository))
                .build()
                .makeExecutableSchema();

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{ books { id name author { name } } }");

        log.info(mapper.writeValueAsString(executionResult.toSpecification()));
        assertThat(executionResult.getErrors()).isEmpty();

    }


    @Test
    public void testUnhandledError() throws JsonProcessingException {

        AuthorRepository authorRepository = new MockedWithErrorAuthorRepository();
        BookRepository bookRepository = new RestBookRepository();

        final GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("my-schema.graphqls")
                .resolvers(new Query(bookRepository), new BookResolver(authorRepository))
                .build()
                .makeExecutableSchema();

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{ books { id name author { name } } }");

        log.info(mapper.writeValueAsString(executionResult.toSpecification()));

        assertThat(executionResult.getErrors()).isNotEmpty();
    }

    @Test
    public void testHandleError() throws JsonProcessingException {

        AuthorRepository authorRepository = new MockedWithErrorAuthorRepository();
        BookRepository bookRepository = new RestBookRepository();

        final GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("my-schema.graphqls")
                .resolvers(new Query(bookRepository), new HandledBookResolver(authorRepository))
                .build()
                .makeExecutableSchema();

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{ books { id name author { name } } }");

        log.info(mapper.writeValueAsString(executionResult.toSpecification()));

        assertThat(executionResult.getErrors()).isNotEmpty();

    }

}
