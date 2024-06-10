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
public class CategoriesDto extends RepresentationModel<CategoriesDto> {

    private int id;

    private String name;

    private String extra_info;

    public String getFullName(){
        return this.name + " - " + this.extra_info;
    }

}
