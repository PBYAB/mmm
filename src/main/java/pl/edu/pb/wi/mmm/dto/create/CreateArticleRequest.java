package pl.edu.pb.wi.mmm.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import pl.edu.pb.wi.mmm.enumeration.ArticleStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class CreateArticleRequest {

    private Long categoryId;

    private String title;

    private String content;

    private ArticleStatus status;
}
