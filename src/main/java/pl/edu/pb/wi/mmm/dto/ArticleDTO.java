package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import pl.edu.pb.wi.mmm.enumeration.ArticleStatus;

import java.time.OffsetDateTime;

@Data
@Builder
@With
public class ArticleDTO {

    @NotNull
    private Long id;

    @NotNull
    private ArticleCategoryDTO category;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private ArticleStatus status;
}
