package pl.edu.pb.wi.mmm.dto.pagescheme;


import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.ArticleDTO;

import java.util.List;

public class ArticleCategoryPageSchema extends PageImpl<ArticleDTO> {
    public ArticleCategoryPageSchema(List<ArticleDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
