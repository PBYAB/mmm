package pl.edu.pb.wi.mmm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nutriment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Nutriment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "energy_kcal_per_100g")
    private Double energyKcalPer100g;

    @Column(name = "fat_per_100g")
    private Double fatPer100g;

    @Column(name = "fiber_per_100g")
    private Double fiberPer100g;

    @Column(name = "proteins_per_100g")
    private Double proteinsPer100g;

    @Column(name = "salt_per_100g")
    private Double saltPer100g;

    @Column(name = "sugars_per_100g")
    private Double sugarsPer100g;

    @Column(name = "sodium_per_100g")
    private Double sodiumPer100g;

    @OneToOne(mappedBy = "nutriment")
    @JsonIgnore
    private Product product;
}

