package br.com.eduardogabriel.books.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BooksDto extends RepresentationModel<BooksDto> { // Extendendo RepresentationModel

    private int id;
    private String name;
    private int publication_year;
    private int edition_year;
    private String author;
    private String isbn;
    private Integer categoryId;  // Campo de ID da categoria, ajustado para Integer para permitir nulos
}
