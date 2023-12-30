package pl.edu.pb.wi.mmm.dto.pagescheme;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.ProductIngredientDTO;

import java.util.List;

public class IngredientPageSchema extends PageImpl<ProductIngredientDTO> {
    public IngredientPageSchema(List<ProductIngredientDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}