package pl.edu.pb.wi.mmm.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pb.wi.mmm.dto.create.CreateArticleRequest;
import pl.edu.pb.wi.mmm.entity.Article;
import pl.edu.pb.wi.mmm.repository.ArticleRepository;

import java.time.OffsetDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleCategoryService articleCategoryService;

    @Transactional
    public Article save(CreateArticleRequest form) {
        Article article = Article.builder()
                .category(articleCategoryService.findById(form.getCategoryId()))
                .title(form.getTitle())
                .content(form.getContent())
                .status(form.getStatus())
                .createdAt(OffsetDateTime.now())
                .build();

        return articleRepository.save(article);
    }

    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article with ID: [%s] not found".formatted(id)));
    }

    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
