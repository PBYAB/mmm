package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.ArticleCategoryDTO;
import pl.edu.pb.wi.mmm.entity.ArticleCategory;

@Mapper(componentModel = "spring")
public interface ArticleCategoryMapper {

    ArticleCategoryDTO map(ArticleCategory articleCategory);
}
