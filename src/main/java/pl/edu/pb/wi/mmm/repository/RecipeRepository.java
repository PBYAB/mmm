package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.mmm.entity.Recipe;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    Boolean existsByNameAndCoverImageUrl(String name, String coverImageUrl);

    @Query(nativeQuery = true, value = "SELECT * FROM recipe ORDER BY RANDOM() LIMIT 1")
    Optional<Recipe> findRandom();
}
