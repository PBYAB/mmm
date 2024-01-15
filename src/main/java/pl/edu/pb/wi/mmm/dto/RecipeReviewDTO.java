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
    private Long id;

    @NotNull
    private Double rating;

    @NotNull
    private Long userId;

    @NotNull
    private String fullName;

    private String comment;
}
