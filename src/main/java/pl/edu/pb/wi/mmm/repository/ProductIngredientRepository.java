package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.ProductIngredient;

import java.util.Optional;
import java.util.Set;

public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long> {

    Optional<ProductIngredient> findByName(String name);

    Set<ProductIngredient> findAllByNameIn(Set<String> collect);
}
