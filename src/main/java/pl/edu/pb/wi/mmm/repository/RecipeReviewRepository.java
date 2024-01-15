package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.RecipeReview;
import pl.edu.pb.wi.mmm.entity.User;

import java.util.List;
import java.util.Optional;

public interface RecipeReviewRepository extends JpaRepository<RecipeReview, Long> {

    @Override
    Optional<RecipeReview> findById(Long aLong);

    @Override
    List<RecipeReview> findAll();

    Page<RecipeReview> findAllByRecipeId(Long recipeId, Pageable pageable);

    Optional<RecipeReview> findByRecipe_IdAndId(Long recipeId, Long id);

    Optional<RecipeReview> findByRecipe_IdAndIdAndAndUserId(Long recipeId, Long id, Long userId);

    Boolean existsByRecipe_IdAndUser(Long recipeId, User user);
}
