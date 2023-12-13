package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.RecipeReview;

import java.util.List;
import java.util.Optional;

public interface RecipeReviewRepository extends JpaRepository<RecipeReview, Long> {

    @Override
    Optional<RecipeReview> findById(Long aLong);

    @Override
    List<RecipeReview> findAll();
}
