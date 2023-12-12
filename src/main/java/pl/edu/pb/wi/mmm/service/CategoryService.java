package pl.edu.pb.wi.mmm.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.create.CreateProductCategoryRequest;
import pl.edu.pb.wi.mmm.entity.Category;
import pl.edu.pb.wi.mmm.repository.CategoryRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(CreateProductCategoryRequest form) {
        var category = Category.builder()
                .name(form.getName())
                .build();

        return save(category);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category with id: [%s] not found".formatted(id)));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Set<Category> findAllByIdIn(Collection<Long> categoryIds) {
        return categoryRepository.findAllByIdIn(categoryIds);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category with name: [%s] not found".formatted(name)));
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional
    public void updateProductCategory(Long id, CreateProductCategoryRequest form) {
        var category = findById(id);
        category.setName(form.getName());
    }

    @Transactional
    public void deleteProductCategory(Long id) {
        var category = findById(id);
        categoryRepository.delete(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
