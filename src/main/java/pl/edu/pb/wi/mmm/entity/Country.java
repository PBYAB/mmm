package pl.edu.pb.wi.mmm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "country")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
<<<<<<<< HEAD:src/main/java/pl/edu/pb/wi/mmm/entity/Country.java
public class Country {
========
public class Ingredient {
>>>>>>>> 2fe8b67 (whatever):src/main/java/pl/edu/pb/wi/mmm/entity/Ingredient.java

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

<<<<<<<< HEAD:src/main/java/pl/edu/pb/wi/mmm/entity/Country.java
    @ManyToMany(mappedBy = "countries")
    @JsonIgnore
    private Set<Product> product;
========
    @Column(name = "vegan")
    private Boolean vegan;

    @Column(name = "vegetarian")
    private Boolean vegetarian;

    @Column(name = "from_palm_oil")
    private Boolean fromPalmOil;

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private Set<Product> products;
>>>>>>>> 2fe8b67 (whatever):src/main/java/pl/edu/pb/wi/mmm/entity/Ingredient.java
}
