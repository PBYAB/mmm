package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Category;
import pl.edu.pb.wi.mmm.entity.ProductIngredient;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Set<Category> findAllByIdIn(Collection<Long> collect);

    Set<Category> findAllByIdIn(Set<Long> categoryIds);

    Set<Category> findAllByNameIn(Set<String> names);
}
