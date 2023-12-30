package pl.edu.pb.wi.mmm.dto.pagescheme;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.ProductIngredientDTO;

import java.util.List;

public class ProductIngredientsPageSchema extends PageImpl<ProductIngredientDTO> {
    public ProductIngredientsPageSchema(List<ProductIngredientDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
