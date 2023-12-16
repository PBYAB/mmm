package pl.edu.pb.wi.mmm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "product_ingredient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vegan")
    private Boolean vegan;

    @Column(name = "vegetarian")
    private Boolean vegetarian;

    @Column(name = "from_palm_oil")
    private Boolean fromPalmOil;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "ingredients")
    @JsonIgnore
    private Set<Product> products;
}


