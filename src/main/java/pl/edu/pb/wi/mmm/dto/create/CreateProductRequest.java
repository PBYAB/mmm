package pl.edu.pb.wi.mmm.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductRequest {

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
    private Set<CreateProductCategoryRequest> categories;

    @NotNull(message = "Brand is required")
    private Set<CreateBrandRequest> brands;

    @NotNull(message = "Allergens is required")
    private Set<CreateAllergenRequest> allergens;

    @NotNull(message = "Ingredients is required")
    private Set<CreateProductIngredientRequest> ingredients;

    @NotNull(message = "IngredientAnalysis is required")
    private CreateProductIngredientAnalysisRequest ingredientAnalysis;

    @NotNull(message = "Nutriment is required")
    private CreateNutrimentRequest nutriment;

    @NotNull(message = "Country is required")
    private Set<CreateCountryRequest> countries;
}
