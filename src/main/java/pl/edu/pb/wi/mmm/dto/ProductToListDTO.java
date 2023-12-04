package pl.edu.pb.wi.mmm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Data
@RequiredArgsConstructor
@Builder
@With
public class ProductToListDTO {

    private Long id;

    private String name;

    private Integer nutriScore;

    private String quantity;

    private Integer novaGroup;
}
