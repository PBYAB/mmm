package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.ProductToListDTO;
import pl.edu.pb.wi.mmm.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductToListMapper {

    ProductToListDTO map(Product product);

    Product map(ProductToListDTO productDTO);
}
