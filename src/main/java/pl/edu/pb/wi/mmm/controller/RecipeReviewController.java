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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.RecipeReviewDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeReviewRequest;
import pl.edu.pb.wi.mmm.dto.mapper.RecipeReviewMapper;
import pl.edu.pb.wi.mmm.dto.pagescheme.RecipeReviewPageSchema;
import pl.edu.pb.wi.mmm.entity.RecipeReview;
import pl.edu.pb.wi.mmm.entity.User;
import pl.edu.pb.wi.mmm.service.RecipeReviewService;

import java.net.URI;


@Tag(name = "RecipeReview", description = "RecipeReview APIs")
@RestController
@RequestMapping(RecipeController.API_RECIPES)
@RequiredArgsConstructor
public class RecipeReviewController {

        private final ValidationHandler validationHandler;

        private final RecipeReviewMapper recipeReviewMapper;

        private final RecipeReviewService recipeReviewService;


    @PostMapping("/{recipeId}/reviews")
    @Operation(summary = "Add review to recipe")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Review created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid input data or validation errors"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User is not allowed to create a review"
            )
    })
    public ResponseEntity<?> addReview(
            @PathVariable Long recipeId,
            @Valid @RequestBody CreateRecipeReviewRequest form,
            BindingResult bindingResult,
            @AuthenticationPrincipal User user
            ) {
        validationHandler.validateAndHandleErrors(bindingResult);


        RecipeReview recipeReview = recipeReviewService.createRecipeReview(recipeId, form, user.getId());

        return ResponseEntity
                .created(URI.create(RecipeController.API_RECIPES + "/" + recipeReview.getId()))
                .build();
    }

    @GetMapping("/{id}/reviews")
    @Operation(summary = "Get all reviews for recipe")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RecipeReviewPageSchema.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User is not allowed to get reviews"
            )
    })
    public ResponseEntity<Page<RecipeReviewDTO>> getReviews(
            @PathVariable Long id,
            Pageable pageable
    ) {
        Page<RecipeReview> recipeReviews = recipeReviewService.findAllByRecipeId(id, pageable);

        return ResponseEntity.ok(recipeReviews.map(recipeReviewMapper::map));
    }

    @PutMapping("/{recipeId}/reviews/{reviewId}")
    @Operation(summary = "Update review")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Review updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid input data or validation errors"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User is not allowed to update review"
            )
    })
    public ResponseEntity<?> updateReview(
            @PathVariable Long recipeId,
            @PathVariable Long reviewId,
            @Valid @RequestBody CreateRecipeReviewRequest form,
            BindingResult bindingResult,
            @AuthenticationPrincipal User user
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);

        recipeReviewService.updateRecipeReview(recipeId, reviewId, form, user.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recipeId}/reviews/{reviewId}")
    @Operation(summary = "Delete review")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Review deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User is not allowed to delete review"
            )
    })
    public ResponseEntity<?> deleteReview(
            @PathVariable Long recipeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal User user
    ) {
        recipeReviewService.deleteRecipeReview(recipeId, reviewId, user.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{recipeId}/reviews/{reviewId}")
    @Operation(summary = "Get review")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Review retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User is not allowed to get review"
            )
    })
    public ResponseEntity<?> getReview(
            @PathVariable Long recipeId,
            @PathVariable Long reviewId
    ) {
        RecipeReview recipeReview = recipeReviewService.findByRecipeAndId(recipeId, reviewId);

        return ResponseEntity.ok(recipeReviewMapper.map(recipeReview));
    }

}
