package pl.edu.pb.wi.mmm.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pb.wi.mmm.dto.create.CreateArticleCategoryRequest;
import pl.edu.pb.wi.mmm.entity.ArticleCategory;
import pl.edu.pb.wi.mmm.repository.ArticleCategoryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleCategoryService {

    private final ArticleCategoryRepository categoryRepository;

    @Transactional
    public ArticleCategory save(CreateArticleCategoryRequest form) {
        categoryRepository.findByName(form.getName()).ifPresent(found -> {
            throw new EntityExistsException("Category with name: [%s] already exists".formatted(found.getName()));
        });

        ArticleCategory category = ArticleCategory.builder()
                .name(form.getName())
                .build();

        return categoryRepository.save(category);
    }

    public ArticleCategory findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID: [%s] not found".formatted(id)));
    }

    public Page<ArticleCategory> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional
    public void deleteById(Long id) {
        ArticleCategory category = findById(id);
        categoryRepository.delete(category);
    }

    @Transactional
    public void update(Long id, CreateArticleCategoryRequest form) {
        ArticleCategory category = findById(id);
        category.setName(form.getName());
    }
}
