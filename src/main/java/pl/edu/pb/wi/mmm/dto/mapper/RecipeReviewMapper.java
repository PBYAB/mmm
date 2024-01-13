package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pb.wi.mmm.dto.RecipeReviewDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeReviewRequest;
import pl.edu.pb.wi.mmm.entity.RecipeReview;

@Mapper(componentModel = "spring")
public interface RecipeReviewMapper {

    @Mapping(target = "fullName", expression = "java(recipeReview.getUser().getFirstName() + ' ' + recipeReview.getUser().getLastName())")
    RecipeReviewDTO map(RecipeReview recipeReview);

    RecipeReview map(RecipeReviewDTO recipeReviewDTO);

    RecipeReview map(CreateRecipeReviewRequest createRecipeReviewRequest);

}
