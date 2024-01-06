package pl.edu.pb.wi.mmm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pb.wi.mmm.dto.create.CreateIngredientRequest;
import pl.edu.pb.wi.mmm.entity.Ingredient;
import pl.edu.pb.wi.mmm.repository.IngredientRepository;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Ingredient with ID: [%s] not found".formatted(id)));
    }

    @Transactional
    public Ingredient createAndSave(CreateIngredientRequest form) {
        Ingredient ingredient = Ingredient.builder()
                .name(form.getName())
                .build();

        return ingredientRepository.save(ingredient);
    }

    public Page<Ingredient> findAll(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    public Ingredient findByName(String name) {
        return ingredientRepository.findByName(name).orElseThrow(() ->
                new RuntimeException("Ingredient with name: [%s] not found".formatted(name)));
    }
}
