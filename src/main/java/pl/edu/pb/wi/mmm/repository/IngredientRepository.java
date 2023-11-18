package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Ingredient;

import java.util.Optional;
import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(String name);

    Set<Ingredient> findAllByNameIn(Set<String> collect);
}
