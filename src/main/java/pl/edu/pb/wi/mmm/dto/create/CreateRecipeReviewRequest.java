package pl.edu.pb.wi.mmm.dto.create;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With

public class CreateRecipeReviewRequest {

    @NotNull(message = "Rating is required")
    private Double rating;

    private String comment;
}
