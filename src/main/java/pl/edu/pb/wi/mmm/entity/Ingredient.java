package pl.edu.pb.wi.mmm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name = "ingredient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Ingredient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "quantity")
    private String quantity;

    @Column(name = "vegan")
    private Boolean vegan;

    @Column(name = "vegetarian")
    private Boolean vegetarian;

    @Column(name = "from_palm_oil")
    private Boolean from_palm_oil;

}
