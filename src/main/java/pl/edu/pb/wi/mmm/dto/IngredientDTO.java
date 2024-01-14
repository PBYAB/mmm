package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class IngredientDTO {

    @NotNull
    private Long id;

    @NotNull(message = "Name is required")
    private String name;
}
