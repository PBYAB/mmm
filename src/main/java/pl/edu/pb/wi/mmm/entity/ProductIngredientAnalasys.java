package pl.edu.pb.wi.mmm.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_ingredient_analysis")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductIngredientAnalasys {

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "vegan")
    private Boolean vegan;

    @Column(name = "vegetarian")
    private Boolean vegetarian;

    @Column(name = "from_palm_oil")
    private Boolean fromPalmOil;

}