package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class NutrimentDTO {

    @NotNull
    private Long id;

    private Double energyKcalPer100g;

    private Double fatPer100g;

    private Double fiberPer100g;

    private Double proteinsPer100g;

    private Double saltPer100g;

    private Double sugarsPer100g;

    private Double sodiumPer100g;
}
