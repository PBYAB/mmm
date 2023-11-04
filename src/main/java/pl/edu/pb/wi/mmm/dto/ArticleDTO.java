package pl.edu.pb.wi.mmm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import pl.edu.pb.wi.mmm.enumeration.ArticleStatus;

import java.time.OffsetDateTime;

@Data
@Builder
@With
public class ArticleDTO {

    private Long id;

    private ArticleCategoryDTO category;

    private String title;

    private String content;

    private OffsetDateTime createdAt;

    private ArticleStatus status;
}
