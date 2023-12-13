package pl.edu.pb.wi.mmm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeReviewRequest;
import pl.edu.pb.wi.mmm.entity.RecipeReview;
import pl.edu.pb.wi.mmm.repository.RecipeReviewRepository;

@Service
@RequiredArgsConstructor
public class RecipeReviewService {

    RecipeReviewRepository recipeReviewRepository;

    @Transactional
    public RecipeReview createRecipeReview(CreateRecipeReviewRequest form) {
        RecipeReview recipeReview = RecipeReview.builder()
                .rating(form.getRating())
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

    public void updateRecipeReview(Long id, CreateRecipeReviewRequest form) {
        RecipeReview recipeReview = findById(id);
        recipeReview.setRating(form.getRating());
        recipeReview.setComment(form.getComment());
    }

    public void deleteRecipeReview(Long id) {
        RecipeReview recipeReview = findById(id);

        recipeReviewRepository.delete(recipeReview);
    }
}
