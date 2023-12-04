package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.entity.ProductIngredient;

@Mapper(componentModel = "spring")
public interface ProductIngredientMapper {

    ProductIngredientMapper map(ProductIngredient productIngredient);

    ProductIngredient map(ProductIngredientMapper productIngredientDTO);
}
