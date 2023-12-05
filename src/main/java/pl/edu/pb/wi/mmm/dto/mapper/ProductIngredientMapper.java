package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.ProductIngredientDTO;
import pl.edu.pb.wi.mmm.entity.ProductIngredient;

@Mapper(componentModel = "spring")
public interface ProductIngredientMapper {

    ProductIngredientDTO map(ProductIngredient productIngredient);

    ProductIngredient map(ProductIngredientDTO productIngredientDTO);
}
