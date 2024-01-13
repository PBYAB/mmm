package pl.edu.pb.wi.mmm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeReviewRequest;
import pl.edu.pb.wi.mmm.entity.RecipeReview;
import pl.edu.pb.wi.mmm.entity.User;
import pl.edu.pb.wi.mmm.repository.RecipeReviewRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeReviewService {

    private final RecipeReviewRepository recipeReviewRepository;

    private final RecipeService recipeService;

    private final UserService userService;

    @Transactional
    public RecipeReview createRecipeReview(Long id, CreateRecipeReviewRequest form, Long userId) {
        if(recipeReviewRepository.findById(id).isPresent()) {
            throw new RuntimeException("RecipeReview with ID: [%s] already exists".formatted(id));
        }

        User user = userService.findById(userId);

        RecipeReview recipeReview = RecipeReview.builder()
                .rating(form.getRating())
                .recipe(recipeService.findById(id))
                .user(user)
                .comment(form.getComment())
                .build();

        return recipeReviewRepository.save(recipeReview);
    }

    public Page<RecipeReview> findAll(Pageable pageable) {
        return recipeReviewRepository.findAll(pageable);
    }

    public RecipeReview findById(Long id) {
        return recipeReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand with ID: [%s] not found".formatted(id)));
    }

    @Transactional
    public void updateRecipeReview(Long recipeId, Long reviewId, CreateRecipeReviewRequest form, Long userId) {
        RecipeReview recipeReview = findByRecipeIdAndReviewIdAndUserId(recipeId, reviewId, userId);

        recipeReview.setRating(form.getRating());
        recipeReview.setComment(form.getComment());
    }

    @Transactional
    public void deleteRecipeReview(Long recipeId, Long reviewId, Long userId) {
        RecipeReview recipeReview = findByRecipeIdAndReviewIdAndUserId(recipeId, reviewId, userId);

        recipeReviewRepository.delete(recipeReview);
    }

    public Page<RecipeReview> findAllByRecipeId(Long recipeId, Pageable pageable) {
        return recipeReviewRepository.findAllByRecipeId(recipeId, pageable);
    }

    public RecipeReview findByRecipeIdAndReviewIdAndUserId(Long recipeId, Long reviewId, Long userId) {
        return recipeReviewRepository.findByRecipe_IdAndIdAndAndUserId(recipeId, reviewId, userId)
                .orElseThrow(() -> new RuntimeException("RecipeReview with ID: [%s] not found".formatted(reviewId)));
    }

    public RecipeReview findByRecipeAndId(Long recipeId, Long reviewId) {
        return recipeReviewRepository.findByRecipe_IdAndId(recipeId, reviewId)
                .orElseThrow(() -> new RuntimeException("RecipeReview with ID: [%s] not found".formatted(reviewId)));
    }
}
