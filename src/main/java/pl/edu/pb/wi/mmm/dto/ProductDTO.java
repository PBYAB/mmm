package pl.edu.pb.wi.mmm.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.edu.pb.wi.mmm.entity.Category;


import java.util.Set;
import java.util.SortedSet;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ProductDTO {

    @NotNull
    private Long id;

    @NotNull
    private String barcode;

    private String name;

    private String quantity;

    private Integer nutriScore;

    private Integer novaGroup;

    private Set<CategoryDTO> categories;

    private Set<BrandDTO> brands;

    private Set<AllergenDTO> allergens;

    private Set<ProductIngredientDTO> ingredients;

    private ProductIngredientAnalysisDTO ingredientAnalysis;

    private NutrimentDTO nutriment;

    private Set<CountryDTO> countries;

    private SortedSet<ProductImageDTO> images;
}
