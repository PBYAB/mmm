package pl.edu.pb.wi.mmm.dto.pagescheme;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.RecipeToListDTO;

import java.util.List;

public class RecipeToListPageSchema extends PageImpl<RecipeToListDTO> {
    public RecipeToListPageSchema(List<RecipeToListDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}