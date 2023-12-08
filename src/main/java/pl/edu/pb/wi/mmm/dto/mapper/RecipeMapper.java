package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.RecipeDTO;
import pl.edu.pb.wi.mmm.dto.RecipeToListDTO;
import pl.edu.pb.wi.mmm.entity.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeDTO map(Recipe recipe);

    Recipe map(RecipeDTO recipeDTO);

    RecipeToListDTO mapListElement(Recipe recipe);
}
