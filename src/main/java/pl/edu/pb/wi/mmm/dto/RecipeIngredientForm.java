package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pb.wi.mmm.enumeration.Unit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeIngredientForm {

    @NotNull(message = "Id is required")
    private Long ingredientId;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Unit is required")
    private Unit unit;
}
