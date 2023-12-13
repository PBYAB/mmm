package pl.edu.pb.wi.mmm.dto;

import jakarta.persistence.Column;
import lombok.*;
import pl.edu.pb.wi.mmm.enumeration.Unit;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RecipeReviewDTO {

    private Long recipeId;

    private Double rating;

    private String comment;

}
