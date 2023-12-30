package pl.edu.pb.wi.mmm.dto.pagescheme;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.edu.pb.wi.mmm.dto.CountryDTO;

import java.util.List;

public class CountryPageSchema extends PageImpl<CountryDTO> {
    public CountryPageSchema(List<CountryDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}