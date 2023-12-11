package pl.edu.pb.wi.mmm.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.entity.Category;
import pl.edu.pb.wi.mmm.repository.ProductCategoryRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;


    public Category findById(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category with id: [%s] not found".formatted(id)));
    }

    public Category save(Category category) {
        return productCategoryRepository.save(category);
    }

    public Category findByName(String name) {
        return productCategoryRepository.findByName(name)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category with name: [%s] not found".formatted(name)));
    }

    public List<Category> findAll() {
        return productCategoryRepository.findAll();
    }

    public Set<Category> findAllByIds(Set<Long> ids) {
        return productCategoryRepository.findAllByIdIn(ids);
    }
}
