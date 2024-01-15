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
import pl.edu.pb.wi.mmm.dto.CanUserCreateReviewDTO;
import pl.edu.pb.wi.mmm.dto.RecipeReviewDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeReviewRequest;
import pl.edu.pb.wi.mmm.dto.mapper.RecipeReviewMapper;
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
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<Void> addReview(
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
    public ResponseEntity<Page<RecipeReviewDTO>> getReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
        );

        Page<RecipeReview> recipeReviews = recipeReviewService.findAllByRecipeId(id, pageable);

        return ResponseEntity.ok(recipeReviews.map(recipeReviewMapper::map));
    }

    @PutMapping("/{recipeId}/reviews/{reviewId}")
    @Operation(summary = "Update review")
    public ResponseEntity<Void> updateReview(
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
    @ApiResponses(value = @ApiResponse(responseCode = "204"))
    public ResponseEntity<Void> deleteReview(
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
    public ResponseEntity<RecipeReviewDTO> getReview(
            @PathVariable Long recipeId,
            @PathVariable Long reviewId
    ) {
        RecipeReview recipeReview = recipeReviewService.findByRecipeAndId(recipeId, reviewId);

        return ResponseEntity.ok(recipeReviewMapper.map(recipeReview));
    }

    @GetMapping("/{recipeId}/reviews/can-review")
    public ResponseEntity<CanUserCreateReviewDTO> checkIfUserReviewed(
            @PathVariable Long recipeId,
            @AuthenticationPrincipal User user
    ) {
        Boolean isReviewed = recipeReviewService.checkIfUserReviewed(recipeId, user);

        return ResponseEntity.ok(new CanUserCreateReviewDTO(!isReviewed));
    }
}
