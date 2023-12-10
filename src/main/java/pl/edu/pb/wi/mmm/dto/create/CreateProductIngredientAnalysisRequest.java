package pl.edu.pb.wi.mmm.dto.create;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class CreateProductIngredientAnalysisRequest {

    @NotNull(message = "Vegan is required")
    private Boolean vegan;

    @NotEmpty(message = "Ingredients description is required")
    private String ingredientsDescription;

    @NotNull(message = "Vegetarian is required")
    private Boolean vegetarian;

    @NotNull(message = "From palm oil is required")
    private Boolean fromPalmOil;
}
