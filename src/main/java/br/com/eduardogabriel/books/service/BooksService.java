package br.com.eduardogabriel.books.service;

import br.com.eduardogabriel.books.dto.BooksDto;
import br.com.eduardogabriel.books.exceptions.ResourceNotFoundException;
import br.com.eduardogabriel.books.mapper.CustomModelMapper;
import br.com.eduardogabriel.books.model.BooksModel;
import br.com.eduardogabriel.books.model.CategoriesModel;
import br.com.eduardogabriel.books.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BooksService {

    @Autowired
    private BooksRepository repository;

    public BooksDto create(BooksDto dto) {
        BooksModel model = CustomModelMapper.toBooksModel(dto);
        BooksModel savedModel = repository.save(model);
        return CustomModelMapper.toBooksDto(savedModel);
    }

    public Page<BooksDto> findByName(String name, Pageable pageable) {
        Page<BooksModel> page = repository.findByNameContainsIgnoreCaseOrderByName(name, pageable);
        return page.map(CustomModelMapper::toBooksDto);
    }

    public List<BooksDto> findAll() {
        List<BooksModel> models = repository.findAll();
        return models.stream()
                .map(CustomModelMapper::toBooksDto)
                .collect(Collectors.toList());
    }

    public BooksDto findById(int id) {
        Optional<BooksModel> optionalModel = repository.findById(id);
        if (optionalModel.isPresent()) {
            return CustomModelMapper.toBooksDto(optionalModel.get());
        } else {
            throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
        }
    }

    public BooksDto update(BooksDto dto) {
        Optional<BooksModel> optionalModel = repository.findById(dto.getId());
        if (optionalModel.isPresent()) {
            BooksModel model = optionalModel.get(); // Carrega o modelo existente
            model.setName(dto.getName());
            model.setPublication_year(dto.getPublication_year());
            model.setEdition_year(dto.getEdition_year());
            model.setAuthor(dto.getAuthor());
            model.setIsbn(dto.getIsbn());
            if (dto.getCategoryId() != null) {
                CategoriesModel category = new CategoriesModel();
                category.setId(dto.getCategoryId());
                model.setCategory(category);
            }
            BooksModel updatedModel = repository.save(model);
            return CustomModelMapper.toBooksDto(updatedModel);
        } else {
            throw new ResourceNotFoundException("Livro não encontrado com o ID: " + dto.getId());
        }
    }

    public void delete(int id) {
        BooksModel model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com o ID: " + id));
        repository.delete(model);
    }
}
