package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.ProductCategoryDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateProductCategoryRequest;
import pl.edu.pb.wi.mmm.entity.Category;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategoryDTO map(Category category);

    Category map(ProductCategoryDTO productCategoryDTO);

    Category map(CreateProductCategoryRequest createProductCategoryRequest);
}
