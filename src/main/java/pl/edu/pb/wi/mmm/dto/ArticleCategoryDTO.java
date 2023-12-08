package pl.edu.pb.wi.mmm.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ArticleCategoryDTO {

    private Long id;

    private String name;
}
