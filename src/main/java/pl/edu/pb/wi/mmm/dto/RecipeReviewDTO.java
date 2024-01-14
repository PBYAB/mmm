package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RecipeReviewDTO {

    @NotNull
    private Double rating;

    @NotNull
    private String fullName;

    private String comment;
}
