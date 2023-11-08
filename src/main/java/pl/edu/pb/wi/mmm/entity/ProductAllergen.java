package pl.edu.pb.wi.mmm.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_allergen")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductAllergen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "allergen_id", nullable = false)
    private Allergen allergen;


}
