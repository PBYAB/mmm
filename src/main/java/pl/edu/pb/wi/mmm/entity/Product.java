package pl.edu.pb.wi.mmm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "nutri_score")
    private Integer nutriScore;

    @Column(name = "nova_group")
    private Integer novaGroup;

    @ManyToMany
    @JoinTable(
            name = "product_brand",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private Set<Brand> brands;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> images;

    @ManyToMany
    @JoinTable(
            name = "product_allergen",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "allergen_id")
    )
    private Set<Allergen> allergens;

    @ManyToMany
    @JoinTable(
            name = "product_ingredient",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_analysis_id", referencedColumnName = "id")
    private ProductIngredientAnalysis ingredientAnalysis;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nutriment_id", referencedColumnName = "id")
    private Nutriment nutriment;

    @ManyToMany
    @JoinTable(
            name = "product_country",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> countries;
}
