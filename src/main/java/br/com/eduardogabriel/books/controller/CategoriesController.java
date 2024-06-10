package br.com.eduardogabriel.books.controller;

import br.com.eduardogabriel.books.dto.CategoriesDto;
import br.com.eduardogabriel.books.model.CategoriesModel;
import br.com.eduardogabriel.books.service.CategoriesService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService service;

    @PostMapping
    public CategoriesDto create(@RequestBody CategoriesDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public CategoriesDto findById(@PathVariable("id") int id) {
        CategoriesDto dto = service.findById(id);
        createLinks(dto);
        return dto;
    }

    @GetMapping("/find/{name}")
    public List<CategoriesDto> findByName(@PathVariable("name") String name) {
        return service.findByName(name);
    }

    @GetMapping
    public CollectionModel<CategoriesDto> findAll() {
        CollectionModel<CategoriesDto> categories = CollectionModel.of(service.findAll());
        for (CategoriesDto category : categories) {
            createLinks(category);
        }
        createCollectionLink(categories);
        return categories;
    }

    @PutMapping("/{id}")
    public CategoriesDto update(@PathVariable("id") int id, @RequestBody CategoriesDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        service.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    public void createLinks(CategoriesDto dto) {
        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()).findById(dto.getId())
                ).withSelfRel()
        );
        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()).delete(dto.getId())
                ).withRel("delete")
        );
    }

    private void createCollectionLink(CollectionModel<CategoriesDto> categories) {
        categories.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()).findAll()
                ).withRel(IanaLinkRelations.COLLECTION)
        );
    }
}
