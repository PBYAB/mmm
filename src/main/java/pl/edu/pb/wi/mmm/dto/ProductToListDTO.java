package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long id;

    private String name;

    @NotNull
    private String barcode;

    private Integer nutriScore;

    private Integer novaGroup;

    private String quantity;

    private ProductImageDTO image;
}
