package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.UserRecipeOfTheDay;

import java.util.Optional;

public interface UserRecipeOfTheDayRepository extends JpaRepository<UserRecipeOfTheDay, Long> {

    Optional<UserRecipeOfTheDay> findFirstByUserIdOrderByDrawnAtDesc(Long userId);
}
