package pl.edu.pb.wi.mmm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeRequest;
import pl.edu.pb.wi.mmm.entity.Ingredient;
import pl.edu.pb.wi.mmm.entity.Recipe;
import pl.edu.pb.wi.mmm.entity.RecipeIngredient;
import pl.edu.pb.wi.mmm.repository.RecipeRepository;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public Recipe createRecipe(CreateRecipeRequest createRecipeRequest) {
        var ingredients = createRecipeRequest.getIngredients().stream()
                .map(ingredientForm -> {
                    var ingredient = Ingredient.builder()
                            .name(ingredientForm.getName())
                            .build();

                    return RecipeIngredient.builder()
                            .ingredient(ingredient)
                            .quantity(ingredientForm.getAmount())
                            .unit(ingredientForm.getUnit())
                            .build();
                })
                .collect(Collectors.toSet());

        return Recipe.builder()
                .name(createRecipeRequest.getName())
                .instructions(createRecipeRequest.getInstructions())
                .servings(createRecipeRequest.getServings())
                .kcalPerServing(createRecipeRequest.getKcalPerServing())
                .recipeIngredients(ingredients)
                .build();
    }

    public Page<Recipe> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe with ID: [%s] not found".formatted(id)));
    }
}
