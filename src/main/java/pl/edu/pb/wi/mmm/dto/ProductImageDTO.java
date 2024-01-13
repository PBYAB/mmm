package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import pl.edu.pb.wi.mmm.enumeration.PhotoSize;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ProductImageDTO {

    @NotBlank
    private String url;

    @NotNull
    private PhotoSize size;
}
