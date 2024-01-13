package pl.edu.pb.wi.mmm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.util.Collection;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@With
public class RecipeDTO {

    private Long id;

    private String name;

    private String instructions;

    private Integer servings;

    private Double kcalPerServing;

    private Collection<RecipeIngredientDTO> ingredients;

    private String coverImageUrl;

    private Integer totalTime;

    private Double averageRating;

    private Set<RecipeReviewDTO> reviews;
}
