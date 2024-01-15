package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pb.wi.mmm.dto.RecipeDTO;
import pl.edu.pb.wi.mmm.dto.RecipeIngredientDTO;
import pl.edu.pb.wi.mmm.dto.RecipeListItem;
import pl.edu.pb.wi.mmm.dto.RecipeReviewDTO;
import pl.edu.pb.wi.mmm.dto.RecipeToListDTO;
import pl.edu.pb.wi.mmm.entity.Recipe;
import pl.edu.pb.wi.mmm.entity.RecipeIngredient;
import pl.edu.pb.wi.mmm.entity.RecipeReview;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeDTO map(Recipe recipe);

    Recipe map(RecipeDTO recipeDTO);

    @Mapping(source = "ingredient.name", target = "name")
    RecipeIngredientDTO map(RecipeIngredient recipeIngredient);

    RecipeListItem mapToListItem(Recipe recipe);

    @Mapping(target = "fullName", expression = "java(review.getUser().getFirstName() + ' ' + review.getUser().getLastName())")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "id")
    RecipeReviewDTO map(RecipeReview review);
}
