package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.IngredientDTO;
import pl.edu.pb.wi.mmm.entity.Ingredient;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientDTO map(Ingredient ingredient);

    Ingredient map(IngredientDTO ingredientDTO);
}