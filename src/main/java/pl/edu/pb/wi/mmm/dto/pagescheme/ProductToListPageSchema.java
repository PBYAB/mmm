package pl.edu.pb.wi.mmm.dto.pagescheme;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.ProductToListDTO;

import java.util.List;

public class ProductToListPageSchema extends PageImpl<ProductToListDTO> {
    public ProductToListPageSchema(List<ProductToListDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}