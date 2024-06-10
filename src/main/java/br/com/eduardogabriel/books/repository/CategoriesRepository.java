package br.com.eduardogabriel.books.repository;

import br.com.eduardogabriel.books.model.CategoriesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends
        JpaRepository<CategoriesModel, Integer> {

    public List<CategoriesModel> findByNameContainsIgnoreCaseOrderByName(String name);

}
