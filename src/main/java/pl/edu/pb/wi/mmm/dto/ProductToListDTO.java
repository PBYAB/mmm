package pl.edu.pb.wi.mmm.dto;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@With
public class ProductToListDTO {

    private Long id;

    private String name;

    private Integer nutriScore;

    private String quantity;

    private Integer novaGroup;
}
