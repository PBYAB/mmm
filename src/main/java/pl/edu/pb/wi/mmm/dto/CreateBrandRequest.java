package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class CreateBrandRequest {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
}
