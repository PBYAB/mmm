package pl.edu.pb.wi.mmm.migration.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nutriment {

    @Field("energy-kcal_100g")
    private Double energyKcalPer100g;

    @Field("fat_100g")
    private Double fatPer100g;

    @Field("saturated-fat_100g")
    private Double saturatedFatPer100g;
 
    @Field("fiber_100g")
    private Double fiberPer100g;

    @Field("proteins_100g")
    private Double proteinPer100g;

    @Field("salt_100g")
    private Double saltPer100g;

    @Field("sugars_100g")
    private Double sugarPer100g;

    @Field("sodium_100g")
    private Double sodiumPer100g;
}