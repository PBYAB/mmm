package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}