package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.ProductIngredientAnalysis;

public interface ProductIngredientAnalysisRepository extends JpaRepository<ProductIngredientAnalysis, Long> {
}