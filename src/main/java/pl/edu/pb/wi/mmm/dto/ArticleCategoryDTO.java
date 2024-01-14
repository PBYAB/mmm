package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ArticleCategoryDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;
}
