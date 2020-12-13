package com.mercadolibre.graphql;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public
class Author {
    int id;
    String name;
}