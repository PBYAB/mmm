package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.ProductImageDTO;
import pl.edu.pb.wi.mmm.entity.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImageDTO map(ProductImage nutriment);

    ProductImage map(ProductImageDTO nutrimentDTO);
}
