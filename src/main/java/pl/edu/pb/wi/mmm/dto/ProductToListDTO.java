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

    private String barcode;

    private Integer nutriScore;

    private Integer novaGroup;

    private String quantity;
}
