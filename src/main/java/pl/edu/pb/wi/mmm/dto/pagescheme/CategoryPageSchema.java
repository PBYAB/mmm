package pl.edu.pb.wi.mmm.dto.pagescheme;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.CategoryDTO;

import java.util.List;

public class CategoryPageSchema extends PageImpl<CategoryDTO> {
    public CategoryPageSchema(List<CategoryDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}