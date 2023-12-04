package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Data
@RequiredArgsConstructor
@Builder
@With
public class IngredientDTO {

    private final Long id;

    @NotBlank(message = "Name is required")
    private final String name;
}
