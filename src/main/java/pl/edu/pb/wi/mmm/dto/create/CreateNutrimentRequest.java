package pl.edu.pb.wi.mmm.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class CreateNutrimentRequest {

    @NotNull(message = "energy_kcal_per_100g is required")
    private Double energyKcalPer100g;

    @NotNull(message = "fat_per_100g is required")
    private Double fatPer100g;

    @NotNull(message = "fiber_per_100g is required")
    private Double fiberPer100g;

    @NotNull(message = "proteins_per_100g is required")
    private Double proteinsPer100g;

    @NotNull(message = "salt_per_100g is required")
    private Double saltPer100g;

    @NotNull(message = "sugars_per_100g is required")
    private Double sugarsPer100g;

    @NotNull(message = "sodium_per_100g is required")
    private Double sodiumPer100g;
}
