package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.RecipeReviewDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeReviewRequest;
import pl.edu.pb.wi.mmm.entity.RecipeReview;

@Mapper(componentModel = "spring")
public interface RecipeReviewMapper {

    RecipeReviewDTO map(RecipeReview recipeReview);

    RecipeReview map(RecipeReviewDTO recipeReviewDTO);

    RecipeReview map(CreateRecipeReviewRequest createRecipeReviewRequest);
}
