package pl.edu.pb.wi.mmm.dto.pagescheme;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.RecipeReviewDTO;

import java.util.List;

public class RecipeReviewPageSchema extends PageImpl<RecipeReviewDTO> {
    public RecipeReviewPageSchema(List<RecipeReviewDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}