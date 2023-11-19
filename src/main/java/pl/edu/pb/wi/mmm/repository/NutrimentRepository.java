package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Nutriment;

public interface NutrimentRepository extends JpaRepository<Nutriment, Long> {
}