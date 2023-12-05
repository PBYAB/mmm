package pl.edu.pb.wi.mmm.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@With
public class ProductIngredientDTO {

    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Vegan is required")
    private Boolean vegan;

    @NotNull(message = "Vegetarian is required")
    private Boolean vegetarian;

    @NotNull(message = "From Palm Oil is required")
    private Boolean fromPalmOil;
}
