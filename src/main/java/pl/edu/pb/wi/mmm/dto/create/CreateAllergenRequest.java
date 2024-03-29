package pl.edu.pb.wi.mmm.dto.create;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAllergenRequest {

    @NotBlank(message = "Name is required")
    private String name;
}
