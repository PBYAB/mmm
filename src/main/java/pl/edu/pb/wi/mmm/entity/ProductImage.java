package pl.edu.pb.wi.mmm.entity;
import jakarta.persistence.*;
import lombok.*;
import pl.edu.pb.wi.mmm.enumeration.PhotoSize;
import pl.edu.pb.wi.mmm.enumeration.ProductPhotoContent;

@Entity
@Table(name = "product_image")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductImage {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "size")
    private Enum<PhotoSize> size;

    @Column(name = "content")
    private Enum<ProductPhotoContent> content;
}
