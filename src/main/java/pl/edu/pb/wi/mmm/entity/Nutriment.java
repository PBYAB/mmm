package pl.edu.pb.wi.mmm.entity;

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

    @Column(name = "energyKcalPer100g")
    private Double energyKcalPer100g;

    @Column(name = "fatPer100g")
    private Double FatPer100g;

    @Column(name = "fiberPer100g")
    private Double fiberPer100g;

    @Column(name = "proteinPer100g")
    private Double proteinPer100g;

    @Column(name = "saltPer100g")
    private Double saltPer100g;

    @Column(name = "sugarPer100g")
    private Double sugarPer100g;

    @Column(name = "sodiumPer100g")
    private Double sodiumPer100g;
}
