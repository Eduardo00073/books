package br.com.eduardogabriel.books.controller;

import br.com.eduardogabriel.books.dto.BooksDto;
import br.com.eduardogabriel.books.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Autowired
    private BooksService service;

    @Autowired
    private PagedResourcesAssembler<BooksDto> assembler;

    @PostMapping
    public ResponseEntity<EntityModel<BooksDto>> create(@RequestBody BooksDto dto) {
        BooksDto createdBook = service.create(dto);
        createLinks(createdBook);
        EntityModel<BooksDto> entityModel = EntityModel.of(createdBook);
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BooksDto>> findById(@PathVariable("id") int id) {
        BooksDto dto = service.findById(id);
        createLinks(dto);
        EntityModel<BooksDto> entityModel = EntityModel.of(dto);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<PagedModel<EntityModel<BooksDto>>> findByName(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "page", required = true, defaultValue = "0") int page,
            @RequestParam(value = "size", required = true, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BooksDto> books = service.findByName(name, pageable);

        // Convertendo Page<BooksDto> para PagedModel<EntityModel<BooksDto>> usando o assembler
        PagedModel<EntityModel<BooksDto>> pagedModel = assembler.toModel(books);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<BooksDto>>> findAll() {
        List<BooksDto> books = service.findAll();
        for (BooksDto book : books) {
            createLinks(book);
        }
        CollectionModel<EntityModel<BooksDto>> booksCollection = CollectionModel.of(
                books.stream().map(EntityModel::of).toList()
        );
        createCollectionLink(booksCollection);
        return new ResponseEntity<>(booksCollection, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BooksDto>> update(@PathVariable("id") int id, @RequestBody BooksDto dto) {
        dto.setId(id);
        BooksDto updatedBook = service.update(dto);
        createLinks(updatedBook);
        EntityModel<BooksDto> entityModel = EntityModel.of(updatedBook);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void createLinks(BooksDto dto) {
        // Link para o próprio recurso (self)
        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getId())
                ).withSelfRel()
        );
        // Link para deletar o recurso
        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).delete(dto.getId())
                ).withRel("delete")
        );

        // Link para a categoria do livro, se existir
        if (dto.getCategoryId() != null && dto.getCategoryId() > 0) {
            dto.add(
                    WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(CategoriesController.class).findById(dto.getCategoryId())
                    ).withRel("category")
            );
        }
    }

    private void createCollectionLink(CollectionModel<EntityModel<BooksDto>> books) {
        // Link para a coleção de recursos (todos os livros)
        books.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).findAll()
                ).withRel(IanaLinkRelations.COLLECTION)
        );
    }
}
