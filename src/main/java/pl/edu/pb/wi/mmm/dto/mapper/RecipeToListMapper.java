package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.entity.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeToListMapper {

    RecipeMapper map(Recipe recipe);

    Recipe map(RecipeMapper recipeDTO);
}
