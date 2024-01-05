package pl.edu.pb.wi.mmm.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeRequest;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeReviewRequest;
import pl.edu.pb.wi.mmm.entity.Ingredient;
import pl.edu.pb.wi.mmm.entity.Recipe;
import pl.edu.pb.wi.mmm.entity.RecipeIngredient;
import pl.edu.pb.wi.mmm.entity.RecipeReview;
import pl.edu.pb.wi.mmm.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;

    @Transactional
    public Recipe createRecipe(CreateRecipeRequest createRecipeRequest, boolean published) {
        Recipe recipe = Recipe.builder()
                .name(createRecipeRequest.getName())
                .instructions(createRecipeRequest.getInstructions())
                .servings(createRecipeRequest.getServings())
                .kcalPerServing(createRecipeRequest.getKcalPerServing())
                .published(published)
                .build();

        Set<RecipeIngredient> ingredients = createRecipeRequest.getIngredients().stream()
                .map(ingredientForm -> {
                    Ingredient ingredient = ingredientService.findById(ingredientForm.getIngredientId());
                    return RecipeIngredient.builder()
                            .ingredient(ingredient)
                            .amount(ingredientForm.getAmount())
                            .unit(ingredientForm.getUnit())
                            .recipe(recipe)
                            .build();
                })
                .collect(Collectors.toSet());

        recipe.setIngredients(ingredients);

        return recipeRepository.save(recipe);
    }

    public Page<Recipe> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe with ID: [%s] not found".formatted(id)));
    }

    public void updateRecipe(Long id, CreateRecipeRequest form) {
        Recipe recipe = findById(id);

        recipe.setName(form.getName());
        recipe.setInstructions(form.getInstructions());
        recipe.setServings(form.getServings());
        recipe.setKcalPerServing(form.getKcalPerServing());

        Set<RecipeIngredient> ingredients = form.getIngredients().stream()
                .map(ingredientForm -> {
                    Ingredient ingredient = ingredientService.findById(ingredientForm.getIngredientId());
                    return RecipeIngredient.builder()
                            .ingredient(ingredient)
                            .amount(ingredientForm.getAmount())
                            .unit(ingredientForm.getUnit())
                            .recipe(recipe)
                            .build();
                })
                .collect(Collectors.toSet());

        recipe.setIngredients(ingredients); // FIXME: nie usuwają się obecne składniki, tylko dodają nowe. Ale bardziej poczytać o tym niż pisać jakieś obejście
    }

    public void addRecipeReview(Long recipeId,Long userId, CreateRecipeReviewRequest form) {
        Recipe recipe = findById(recipeId);
        RecipeReview review = new RecipeReview();
        review.setRating(form.getRating());
        review.setComment(form.getComment());
        review.setRecipe(recipe);
        review.setUserId(userId);
        Set<RecipeReview>recipeReviews = recipe.getReviews();
        recipeReviews.add(review);

        recipe.setReviews(recipeReviews);
        recipeRepository.save(recipe);
    }

    public void deleteById(Long id) {
        Recipe recipe = findById(id);
        recipeRepository.delete(recipe);
    }

    public Page<Recipe> findAll(String name, List<Integer> servings, Double minKcalPerServing, Double maxKcalPerServing, Pageable pageable) {
        Specification<Recipe> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (servings != null && !servings.isEmpty()) {
                predicates.add(root.get("servings").in(servings));
            }
            if (minKcalPerServing != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("kcalPerServing"), minKcalPerServing));
            }
            if (maxKcalPerServing != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("kcalPerServing"), maxKcalPerServing));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return recipeRepository.findAll(spec, pageable);
    }

    public Boolean existsByNameAndUrl(String name, String url) {
        return recipeRepository.existsByNameAndCoverImageUrl(name, url);
    }
}
