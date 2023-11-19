package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Category;

import java.util.Optional;
import java.util.Set;

public interface ProductCategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Set<Category> findAllByNameIn(Set<String> collect);
}
