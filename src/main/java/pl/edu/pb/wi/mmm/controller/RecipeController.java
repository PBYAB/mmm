package pl.edu.pb.wi.mmm.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeRequest;
import pl.edu.pb.wi.mmm.dto.RecipeDTO;
import pl.edu.pb.wi.mmm.dto.mapper.RecipeMapper;
import pl.edu.pb.wi.mmm.entity.Recipe;
import pl.edu.pb.wi.mmm.service.RecipeService;

import java.net.URI;

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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Recipe created successfully",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid input data or validation errors"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User is not allowed to create a recipe"
            )
    })
    public ResponseEntity<?> createRecipe(
            @RequestBody @Valid CreateRecipeRequest createRecipeRequest,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        Recipe recipe = recipeService.createRecipe(createRecipeRequest);
        return ResponseEntity
                .created(URI.create(API_RECIPES + "/" + recipe.getId()))
                .body(recipe);
    }

    @GetMapping
    @Operation(summary = "Get a list of all recipes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            )
    })
    public ResponseEntity<Page<Recipe>> getRecipes(
            Pageable pageable
    ) {
        return ResponseEntity.ok(recipeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single recipe")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Recipe with given ID does not exist"
            )
    })
    public ResponseEntity<RecipeDTO> getRecipe(
            @PathVariable Long id
    ) {
        var recipe = recipeService.findById(id);

        return ResponseEntity.ok(recipeMapper.map(recipe));
    }
}
