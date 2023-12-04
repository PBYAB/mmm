package pl.edu.pb.wi.mmm.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_ingredient_analysis")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductIngredientAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "ingredientAnalysis")
    @JsonIgnore
    private Product product;

    @Column(name = "vegan")
    private Boolean vegan;

    @Column(name = "ingredients_description")
    private String ingredientsDescription;

    @Column(name = "vegetarian")
    private Boolean vegetarian;

    @Column(name = "from_palm_oil")
    private Boolean fromPalmOil;
}


