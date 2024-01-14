package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@With
public class RecipeToListDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private Integer servings;

    private String coverImageUrl;

    private Integer totalTime;
}
