package pl.edu.pb.wi.mmm.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ProductIngredientDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    private Boolean vegan;

    private Boolean vegetarian;

    private Boolean fromPalmOil;
}
