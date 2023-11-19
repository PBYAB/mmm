package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Brand;
import pl.edu.pb.wi.mmm.entity.Nutriment;

public interface ProductNutrimentRepository extends JpaRepository<Nutriment, Long> {
}
