package pl.edu.pb.wi.mmm.dto.pagescheme;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.AllergenDTO;

import java.util.List;

public class AllergenPageSchema extends PageImpl<AllergenDTO> {
    public AllergenPageSchema(List<AllergenDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
