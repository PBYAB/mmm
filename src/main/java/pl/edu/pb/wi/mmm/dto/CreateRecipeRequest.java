package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRecipeRequest {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String instructions;

    private Integer servings;

    private Double kcalPerServing;

    private Collection<RecipeIngredientForm> ingredients;
}
