package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.util.Set;

@Data
@RequiredArgsConstructor
@Builder
@With
public class RecipeDTO {

    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Instructions are required")
    private String instructions;

    @NotNull(message = "Servings are required")
    private Integer servings;

    @NotNull(message = "KcalPerServing time is required")
    private Double kcalPerServing;

    @NotNull(message = "Ingredients time is required")
    private Set<RecipeIngredientForm> recipeIngredients;
}
