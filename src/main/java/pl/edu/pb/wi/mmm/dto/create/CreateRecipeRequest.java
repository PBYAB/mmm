package pl.edu.pb.wi.mmm.dto.create;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pb.wi.mmm.dto.RecipeIngredientForm;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRecipeRequest {

    private String name;

    @NotBlank(message = "Instructions are required")
    private String instructions;

    private Integer servings;

    private Double kcalPerServing;

    private Collection<RecipeIngredientForm> ingredients;

    private String coverImageUrl;

    private Integer totalTime;
}
