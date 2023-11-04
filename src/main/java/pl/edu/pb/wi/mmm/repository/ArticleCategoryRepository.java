package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.ArticleCategory;

import java.util.Optional;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Long> {

    Optional<ArticleCategory> findByName(String name);
}