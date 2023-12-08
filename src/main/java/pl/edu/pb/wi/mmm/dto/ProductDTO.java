package pl.edu.pb.wi.mmm.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Barcode is required")
    private String barcode;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Quantity is required")
    private String quantity;

    @NotNull(message = "NutriScore is required")
    private Integer nutriScore;

    @NotNull(message = "NovaGroup is required")
    private Integer novaGroup;

    @NotNull(message = "Categories is required")
    private Set<ProductCategoryDTO> categories;

    @NotNull(message = "Brand is required")
    private Set<BrandDTO> brands;

    @NotNull(message = "Allergens is required")
    private Set<AllergenDTO> allergens;

    private Set<ProductIngredientDTO> ingredients;

    @NotNull(message = "IngredientAnalysis is required")
    private ProductIngredientAnalysisDTO ingredientAnalysis;

    @NotNull(message = "Nutriment is required")
    private NutrimentDTO nutriment;

    @NotNull(message = "Country is required")
    private Set<CountryDTO> countries;
}
