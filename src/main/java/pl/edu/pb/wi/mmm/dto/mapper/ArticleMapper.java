package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.ArticleDTO;
import pl.edu.pb.wi.mmm.entity.Article;

@Mapper(componentModel = "spring", uses = ArticleCategoryMapper.class)
public interface ArticleMapper {

    ArticleDTO map(Article article);
}
