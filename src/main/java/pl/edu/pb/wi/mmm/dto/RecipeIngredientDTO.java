package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import pl.edu.pb.wi.mmm.enumeration.Unit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RecipeIngredientDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private Double amount;

    private Unit unit;
}
