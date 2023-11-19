package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Brand;

import java.util.Optional;
import java.util.Set;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String brand);

    Set<Brand> findAllByNameIn(Set<String> brandNames);
}
