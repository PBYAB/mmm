package pl.edu.pb.wi.mmm.dto;

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

    private Long id;

    private String name;

    private Double amount;

    private Unit unit;
}
