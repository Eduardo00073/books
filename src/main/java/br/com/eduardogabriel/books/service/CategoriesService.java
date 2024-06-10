package br.com.eduardogabriel.books.service;

import br.com.eduardogabriel.books.dto.CategoriesDto;
import br.com.eduardogabriel.books.exceptions.ResourceNotFoundException;
import br.com.eduardogabriel.books.mapper.CustomModelMapper;
import br.com.eduardogabriel.books.model.CategoriesModel;
import br.com.eduardogabriel.books.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository repository;

    //    public CategoriesModel create(CategoriesModel model){
//        return repository.save(model);
//    }
    public CategoriesDto create(CategoriesDto dto){
        CategoriesModel model = CustomModelMapper.parseObject(dto, CategoriesModel.class);
        return CustomModelMapper.parseObject(repository.save(model), CategoriesDto.class);
    }

//    public CategoriesModel findById(int id){
//        return repository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException(null));
//    }

    public CategoriesDto findById(int id){
        CategoriesModel model = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Não Encontrado!")
        );
        return CustomModelMapper.parseObject(model, CategoriesDto.class);
    }
//this approach returns model objects
//    public List<CategoriesModel> findByName(String name){
//        return repository.findByNameContainsIgnoreCaseOrderByName(name);
//    }

    public List<CategoriesDto> findByName(String name){
        List<CategoriesModel> brands = repository.findByNameContainsIgnoreCaseOrderByName(name);
        return CustomModelMapper.parseObjectList(brands, CategoriesDto.class);
    }

//    public List<CategoriesModel> findAll(){ return repository.findAll(); }

    public List<CategoriesDto> findAll(){
        var brands = repository.findAll();
        return CustomModelMapper.parseObjectList(brands, CategoriesDto.class);
    }

    public CategoriesDto update(CategoriesDto dto){
        CategoriesModel found = repository.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Não encontrado!"));
        found.setName(dto.getName());
        found.setExtra_info(dto.getExtra_info());
        return CustomModelMapper.parseObject(repository.save(found), CategoriesDto.class);
    }

    public void delete(int id){
        var found = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Não Encontrado"));
        repository.delete(found);
    }

}
