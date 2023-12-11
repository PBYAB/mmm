package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Allergen;

import java.util.Optional;
import java.util.Set;

public interface AllergenRepository extends JpaRepository<Allergen,  Long> {
    Optional<Allergen> findByName(String allergenName);

    Set<Allergen> findAllByNameIn(Set<String> allergenNames);

    Set<Allergen> findAllByIdIn(Set<Long> allergenIds);
}
