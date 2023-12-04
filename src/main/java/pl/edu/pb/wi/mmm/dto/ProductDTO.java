package pl.edu.pb.wi.mmm.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.Set;

@Data
@RequiredArgsConstructor
@Builder
@With
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
    private Set<CategoryDTO> categories;

    @NotNull(message = "Brand is required")
    private Set<BrandDTO> brands;

    @NotNull(message = "Allergens is required")
    private Set<AllergenDTO> allergens;

    @NotNull(message = "Ingredients is required")
    private Set<ProductIngredientDTO> ingredients;

    @NotNull(message = "IngredientAnalysis is required")
    private ProductIngredientAnalysisDTO ingredientAnalysis;

    @NotNull(message = "Nutriment is required")
    private NutrimentDTO nutriment;

    @NotNull(message = "Country is required")
    private Set<CountryDTO> countries;
}
