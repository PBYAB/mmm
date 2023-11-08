package pl.edu.pb.wi.mmm.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allergen")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

}
