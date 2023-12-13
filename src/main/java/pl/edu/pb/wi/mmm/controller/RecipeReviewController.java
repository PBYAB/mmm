package pl.edu.pb.wi.mmm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.RecipeReviewDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeReviewRequest;
import pl.edu.pb.wi.mmm.dto.mapper.RecipeReviewMapper;
import pl.edu.pb.wi.mmm.entity.RecipeReview;
import pl.edu.pb.wi.mmm.service.RecipeReviewService;

import java.net.URI;


@Tag(name = "RecipeReview", description = "RecipeReview APIs")
@RestController
@RequestMapping(RecipeReviewController.API_RECIPE_REVIEW)
@RequiredArgsConstructor
public class RecipeReviewController {


        public static final String API_RECIPE_REVIEW= "/api/v1/review";

        private final ValidationHandler validationHandler;

        private final RecipeReviewMapper recipeReviewMapper;

        private final RecipeReviewService recipeReviewService;

        @GetMapping
        @Operation(summary = "Get a list of all product ingredients")
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK",
                        content = {
                                @Content(
                                        schema = @Schema(implementation = RecipeReview.class)
                                )
                        }
                )
        })
        public ResponseEntity<Page<RecipeReviewDTO>> getRecipeReview(
                Pageable pageable
        ) {
            return ResponseEntity.ok(recipeReviewService.findAll(pageable).map(recipeReviewMapper::map));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get a product ingredient by ID")
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK",
                        content = {
                                @Content(
                                        schema = @Schema(implementation = RecipeReview.class)
                                )
                        }
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Product ingredient not found"
                )
        })
        public ResponseEntity<RecipeReviewDTO> getProductIngredient(
                @PathVariable Long id
        ) {
            var recipeReview = recipeReviewService.findById(id);
            return ResponseEntity.ok(recipeReviewMapper.map(recipeReview));
        }


        @PostMapping
        @Operation(summary = "Create a new product ingredient")
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Product ingredient created successfully",
                        content = {
                                @Content(
                                        schema = @Schema(implementation = RecipeReview.class)
                                )
                        }
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request - Invalid input data or validation errors"
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden - Access denied"
                )
        })
        public ResponseEntity<RecipeReview> createRecipeReview(
                @Valid @org.springframework.web.bind.annotation.RequestBody CreateRecipeReviewRequest reviewRequest,
                BindingResult bindingResult
        ) {
            validationHandler.validateAndHandleErrors(bindingResult);
            RecipeReview createdRecipeReview = recipeReviewService.createRecipeReview(reviewRequest);
            return ResponseEntity
                    .created(URI.create(API_RECIPE_REVIEW + "/" + createdRecipeReview.getId()))
                    .build();
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update an existing country")
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Country updated successfully"
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request - Invalid input data or validation errors"
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden - Access denied"
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Country not found"
                )
        })
        public ResponseEntity<?> updateRecipeReview(
                @PathVariable Long id,
                @Valid @org.springframework.web.bind.annotation.RequestBody CreateRecipeReviewRequest form,
                BindingResult bindingResult
        ) {
            validationHandler.validateAndHandleErrors(bindingResult);
            recipeReviewService.updateRecipeReview(id, form);
            return ResponseEntity.ok().build();
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete an existing category")
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Country deleted successfully"
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden - Access denied"
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Country not found"
                )
        })
        public ResponseEntity<?> deleteRecipeReview(
                @PathVariable Long id
        ) {
            recipeReviewService.deleteRecipeReview(id);
            return ResponseEntity.noContent().build();
        }
}
