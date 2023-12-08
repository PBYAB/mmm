package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class BrandDTO {

        private Long id;

        @NotBlank(message = "Name is required")
        private String name;
}
