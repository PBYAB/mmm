package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ProductIngredientAnalysisDTO {

    @NotNull
    private Long id;

//    @NotNull(message = "Product is required")
//    private ProductDTO product;

    private Boolean vegan;

    private String ingredientsDescription;

    private Boolean vegetarian;

    private Boolean fromPalmOil;
}
