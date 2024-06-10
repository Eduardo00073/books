package br.com.eduardogabriel.books.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "books")
public class BooksModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int publication_year;

    @Column(name = "edition_year", nullable = false)
    private int edition_year;

    @Column(nullable = false, length = 30)
    private String author;

    @Column(nullable = false, length = 16)
    private String isbn;

    // Relacionamento com CategoriesModel
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoriesModel category;  // Definindo a relação com a categoria
}
