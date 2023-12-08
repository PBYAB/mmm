package pl.edu.pb.wi.mmm.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class CreateArticleCategoryRequest {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
}
