package pl.edu.pb.wi.mmm.dto;

import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@With
public class RecipeToListDTO {

    private Long id;

    private String name;

    private Integer servings;

    private String coverImageUrl;

    private Integer totalTime;
}
