package pl.edu.pb.wi.mmm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.create.CreateProductIngredientRequest;
import pl.edu.pb.wi.mmm.entity.ProductIngredient;
import pl.edu.pb.wi.mmm.repository.ProductIngredientRepository;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductIngredientService {

    private final ProductIngredientRepository productIngredientRepository;

    public Set<ProductIngredient> findAllByIds(Set<Long> ids) {
        return productIngredientRepository.findAllByIdIn(ids);
    }

    public ProductIngredient findById(Long id) {
        return productIngredientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product ingredient with ID: [%s] not found".formatted(id)));
    }

    public Page<ProductIngredient> findAll(Pageable pageable) {
        return productIngredientRepository.findAll(pageable);
    }

    @Transactional
    public ProductIngredient createProductIngredient(CreateProductIngredientRequest form) {
        var productIngredient = ProductIngredient.builder()
                .name(form.getName())
                .vegan(form.getVegan())
                .fromPalmOil(form.getFromPalmOil())
                .vegetarian(form.getVegetarian())
                .build();
        return productIngredientRepository.save(productIngredient);
    }

    @Transactional
    public void deleteProductIngredient(Long id) {
        ProductIngredient productIngredient = findById(id);
        productIngredientRepository.delete(productIngredient);
    }

    @Transactional
    public void updateProductIngredient(Long id, CreateProductIngredientRequest form) {
        var productIngredient = findById(id);
        productIngredient.setName(form.getName());
        productIngredient.setFromPalmOil(form.getFromPalmOil());
        productIngredient.setVegan(form.getVegan());
        productIngredient.setVegetarian(form.getVegetarian());

    }

}
