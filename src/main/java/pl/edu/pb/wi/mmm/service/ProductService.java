package pl.edu.pb.wi.mmm.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.create.CreateProductRequest;
import pl.edu.pb.wi.mmm.dto.mapper.*;
import pl.edu.pb.wi.mmm.entity.Product;
import pl.edu.pb.wi.mmm.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductIngredientAnalysisMapper productIngredientAnalysisMapper;

    private final NutrimentMapper nutrimentMapper;


    private final ProductIngredientService productIngredientService;

    private final AllergenService allergenService;

    private final CountryService countryService;

    private final BrandService brandService;

    private final ProductCategoryService productCategoryService;


    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with id: [%s] not found".formatted(id)));
    }

    @Transactional
    public Product save(CreateProductRequest form) {
        var product = productMapper.map(form);
        
        return productRepository.save(product);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public void updateProduct(Long id, CreateProductRequest form) {
        var product = findById(id);

        product.setName(form.getName());
        product.setBarcode(form.getBarcode());
        product.setQuantity(form.getQuantity());
        product.setNutriScore(form.getNutriScore());
        product.setNovaGroup(form.getNovaGroup());
        product.setCategories(productCategoryService.findAllByIds(form.getCategoriesId()));
        product.setBrands(brandService.findAllByIds(form.getBrandsId()));
        product.setAllergens(allergenService.findAllByIds(form.getAllergensId()));
        product.setIngredients(productIngredientService.findAllByIds(form.getIngredientsId()));
        product.setIngredientAnalysis(productIngredientAnalysisMapper.map(form.getIngredientAnalysis()));
        product.setNutriment(nutrimentMapper.map(form.getNutriment()));
        product.setCountries(countryService.findAllByIds(form.getCountriesId()));
    }

    @Transactional
    public void deleteProduct(Long id) {
        var product = findById(id);

        productRepository.delete(product);
    }

    public Product findByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with barcode: [%s] not found".formatted(barcode)));
    }
}
