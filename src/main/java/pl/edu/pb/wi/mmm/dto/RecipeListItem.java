package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class RecipeListItem {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private Integer servings;

    private Double kcalPerServing;

    private String coverImageUrl;

    private Integer totalTime;

    private Double averageRating;
}
