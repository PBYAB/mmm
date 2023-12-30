package pl.edu.pb.wi.mmm.dto.pagescheme;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.BrandDTO;

import java.util.List;

public class BrandPageSchema extends PageImpl<BrandDTO> {
    public BrandPageSchema(List<BrandDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}