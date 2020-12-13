package com.mercadolibre.graphql;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();

}
