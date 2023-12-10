package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.IngredientDTO;
import pl.edu.pb.wi.mmm.dto.IngredientListItem;
import pl.edu.pb.wi.mmm.dto.create.CreateIngredientRequest;
import pl.edu.pb.wi.mmm.entity.Ingredient;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientDTO map(Ingredient ingredient);

    IngredientListItem mapToListItem(Ingredient ingredient);

    Ingredient map(IngredientDTO ingredientDTO);

    Ingredient map(CreateIngredientRequest createIngredientRequest);
}