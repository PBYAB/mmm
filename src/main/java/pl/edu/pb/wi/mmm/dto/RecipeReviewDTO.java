package pl.edu.pb.wi.mmm.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RecipeReviewDTO {

    private Long id;

    private Double rating;

    private String comment;

}
