package pl.edu.pb.wi.mmm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_brand")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
