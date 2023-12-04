package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.entity.ProductIngredientAnalysis;


@Mapper(componentModel = "spring")
public interface ProductIngredientAnalysisMapper {

    ProductIngredientAnalysisMapper map(ProductIngredientAnalysis productIngredientAnalysis);

    ProductIngredientAnalysis map(ProductIngredientAnalysisMapper productIngredientAnalysisDTO);
}
