package br.com.eduardogabriel.books.mapper;

import br.com.eduardogabriel.books.dto.BooksDto;
import br.com.eduardogabriel.books.dto.CategoriesDto;
import br.com.eduardogabriel.books.model.BooksModel;
import br.com.eduardogabriel.books.model.CategoriesModel;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CustomModelMapper {

    private static final ModelMapper mapper = new ModelMapper();

    // Método genérico para mapear um objeto de uma classe para outra
    public static <Origin, Destination> Destination parseObject(
            Origin originClass, Class<Destination> destinationClass) {
        return mapper.map(originClass, destinationClass);
    }

    // Método genérico para mapear uma lista de objetos de uma classe para outra
    public static <Origin, Destination> List<Destination> parseObjectList(
            List<Origin> originList, Class<Destination> destinationClass) {
        List<Destination> destinationList = new ArrayList<>();
        for (Origin o : originList) {
            destinationList.add(mapper.map(o, destinationClass));
        }
        return destinationList;
    }

    // Método específico para mapear BooksModel para BooksDto, usando apenas categoryId
    public static BooksDto toBooksDto(BooksModel model) {
        BooksDto dto = parseObject(model, BooksDto.class);
        if (model.getCategory() != null) {
            dto.setCategoryId(model.getCategory().getId());
        }
        return dto;
    }

    // Método específico para mapear BooksDto para BooksModel, usando categoryId
    public static BooksModel toBooksModel(BooksDto dto) {
        BooksModel model = parseObject(dto, BooksModel.class);
        if (dto.getCategoryId() != null && dto.getCategoryId() > 0) {  // Verifica se o categoryId é válido
            CategoriesModel category = new CategoriesModel();
            category.setId(dto.getCategoryId());
            model.setCategory(category);
        }
        return model;
    }

    // Método específico para mapear CategoriesModel para CategoriesDto
    public static CategoriesDto toCategoriesDto(CategoriesModel model) {
        return parseObject(model, CategoriesDto.class);
    }

    // Método específico para mapear CategoriesDto para CategoriesModel
    public static CategoriesModel toCategoriesModel(CategoriesDto dto) {
        return parseObject(dto, CategoriesModel.class);
    }
}
