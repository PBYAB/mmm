package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.ProductDTO;
import pl.edu.pb.wi.mmm.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO map(Product product);

    Product map(ProductDTO productDTO);
}
