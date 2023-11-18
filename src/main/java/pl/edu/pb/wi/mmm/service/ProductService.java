package pl.edu.pb.wi.mmm.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.entity.Product;
import pl.edu.pb.wi.mmm.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with id: [%s] not found".formatted(id)));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }


}
