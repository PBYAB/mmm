package pl.edu.pb.wi.mmm.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.CanUserShakeRecipeDTO;
import pl.edu.pb.wi.mmm.dto.RecipeDTO;
import pl.edu.pb.wi.mmm.dto.RecipeListItem;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeRequest;
import pl.edu.pb.wi.mmm.dto.mapper.RecipeMapper;
import pl.edu.pb.wi.mmm.entity.Recipe;
import pl.edu.pb.wi.mmm.entity.User;
import pl.edu.pb.wi.mmm.migration.recipe.RecipeMigrationService;
import pl.edu.pb.wi.mmm.service.RecipeService;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Tag(name = "Recipe", description = "Recipe APIs")
@RestController
@RequestMapping(RecipeController.API_RECIPES)
@RequiredArgsConstructor
public class RecipeController {

    public static final String API_RECIPES = "/api/v1/recipes";

    private final RecipeService recipeService;

    private final ValidationHandler validationHandler;

    private final RecipeMapper recipeMapper;


    @PostMapping
    @Operation(summary = "Create a new recipe")
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<Void> createRecipe(
            @RequestBody @Valid CreateRecipeRequest createRecipeRequest,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        Recipe recipe = recipeService.createRecipe(createRecipeRequest, false);

        return ResponseEntity
                .created(URI.create(API_RECIPES + "/" + recipe.getId()))
                .build();
    }


    @GetMapping
    @Operation(summary = "Get all recipes")
    public ResponseEntity<Page<RecipeListItem>> getRecipes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Integer> servings,
            @RequestParam(required = false) Double minKcalPerServing,
            @RequestParam(required = false) Double maxKcalPerServing,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(org.springframework.data.domain.Sort.Direction.fromString(sortDirection), sortBy)
        );

        return ResponseEntity.ok(recipeService.findAll(name, servings, minKcalPerServing, maxKcalPerServing, pageable).map(recipeMapper::mapToListItem));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update")
    public ResponseEntity<Void> updateRecipeById(
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody CreateRecipeRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);

        recipeService.updateRecipe(id, form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get recipe by ID")
    public ResponseEntity<RecipeDTO> getById(
            @PathVariable Long id
    ) {
        var recipe = recipeService.findById(id);

        return ResponseEntity.ok(recipeMapper.map(recipe));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete recipe by ID")
    @ApiResponses(value = @ApiResponse(responseCode = "204"))
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id
    ) {
        recipeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/recipe-of-the-day")
    public ResponseEntity<RecipeDTO> getUserRecipeOfTheDay(
            @AuthenticationPrincipal User user
    ) {
        var recipeOfTheDay = recipeService.findUserRecipeOfTheDay(user);

        return ResponseEntity.ok(recipeMapper.map(recipeOfTheDay.getRecipe()));
    }

    @GetMapping("/user/recipe-of-the-day/can-shake")
    public ResponseEntity<CanUserShakeRecipeDTO> canShakeRecipe(
            @AuthenticationPrincipal User user
    ) {
        var canShakeRecipe = recipeService.canShakeRecipe(user);

        return ResponseEntity.ok(new CanUserShakeRecipeDTO(canShakeRecipe));
    }


}
