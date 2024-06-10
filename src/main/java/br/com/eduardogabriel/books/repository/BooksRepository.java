package br.com.eduardogabriel.books.repository;

import br.com.eduardogabriel.books.model.BooksModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<BooksModel, Integer> {

    // Método para buscar livros pelo nome, ignorando maiúsculas e minúsculas, ordenados por nome
    Page<BooksModel> findByNameContainsIgnoreCaseOrderByName(String name, Pageable pageable);

}
