package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findByName(String name);
}
