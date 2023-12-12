package pl.edu.pb.wi.mmm.dto.mapper;

import pl.edu.pb.wi.mmm.dto.ProductDTO;
import pl.edu.pb.wi.mmm.dto.ProductToListDTO;
import pl.edu.pb.wi.mmm.entity.Product;

public interface ProductMapper {

    ProductDTO map(Product product);

    Product map(ProductDTO productDTO);

    ProductToListDTO mapToListElement(Product product);


}
