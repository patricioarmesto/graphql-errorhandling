package com.mercadolibre.graphql;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class Book {
    int id;
    String name;
    int authorId;
}