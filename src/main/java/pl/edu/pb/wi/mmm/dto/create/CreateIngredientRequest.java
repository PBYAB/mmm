package pl.edu.pb.wi.mmm.dto.create;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class CreateIngredientRequest {

    @NotBlank(message = "Name is required")
    private String name;
}
