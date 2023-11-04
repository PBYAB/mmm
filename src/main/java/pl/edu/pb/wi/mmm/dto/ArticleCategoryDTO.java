package pl.edu.pb.wi.mmm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Data
@RequiredArgsConstructor
@Builder
@With
public class ArticleCategoryDTO {

    private final Long id;

    private final String name;
}
