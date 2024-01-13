package pl.edu.pb.wi.mmm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;

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

    private ProductImageDTO image;
}
