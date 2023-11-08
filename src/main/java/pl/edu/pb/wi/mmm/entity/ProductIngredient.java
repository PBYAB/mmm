package pl.edu.pb.wi.mmm.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
