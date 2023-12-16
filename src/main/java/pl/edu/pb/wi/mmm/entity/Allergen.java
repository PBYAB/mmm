package pl.edu.pb.wi.mmm.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "allergens", cascade = CascadeType.ALL)
    private Set<Product> products;
}
