package pl.edu.pb.wi.mmm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
}
