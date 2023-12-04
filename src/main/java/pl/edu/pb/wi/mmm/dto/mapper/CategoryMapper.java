package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.CategoryDTO;
import pl.edu.pb.wi.mmm.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO map(Category category);

    Category map(CategoryDTO categoryDTO);
}
