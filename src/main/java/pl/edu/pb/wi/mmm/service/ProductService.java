package pl.edu.pb.wi.mmm.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.create.CreateProductRequest;
import pl.edu.pb.wi.mmm.dto.mapper.*;
import pl.edu.pb.wi.mmm.entity.Allergen;
import pl.edu.pb.wi.mmm.entity.Brand;
import pl.edu.pb.wi.mmm.entity.Category;
import pl.edu.pb.wi.mmm.entity.Country;
import pl.edu.pb.wi.mmm.entity.Product;
import pl.edu.pb.wi.mmm.entity.ProductIngredient;
import pl.edu.pb.wi.mmm.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductIngredientAnalysisMapper productIngredientAnalysisMapper;

    private final NutrimentMapper nutrimentMapper;


    private final ProductIngredientService productIngredientService;

    private final AllergenService allergenService;

    private final CountryService countryService;

    private final BrandService brandService;

    private final CategoryService categoryService;


    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with id: [%s] not found".formatted(id)));
    }

    @Transactional
    public Product save(CreateProductRequest form) {
        Set<Brand> brands = brandService.findAllByIds(form.getBrandsId());
        Set<Category> categories = categoryService.findAllByIdIn(form.getCategoriesId());
        Set<Allergen> allergens = allergenService.findAllByIds(form.getAllergensId());
        Set<ProductIngredient> productIngredients = productIngredientService.findAllByIds(form.getIngredientsId());
        Set<Country> countries = countryService.findAllByIds(form.getCountriesId());

        Product product = Product.builder()
                .name(form.getName())
                .barcode(form.getBarcode())
                .quantity(form.getQuantity())
                .nutriScore(form.getNutriScore())
                .novaGroup(form.getNovaGroup())
                .brands(brands)
                .categories(categories)
                .allergens(allergens)
                .ingredients(productIngredients)
                .ingredientAnalysis(productIngredientAnalysisMapper.map(form.getIngredientAnalysis()))
                .nutriment(nutrimentMapper.map(form.getNutriment()))
                .countries(countries)
                .build();
        
        return productRepository.save(product);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public void updateProduct(Long id, CreateProductRequest form) {
        Product product = findById(id);

        product.setName(form.getName());
        product.setBarcode(form.getBarcode());
        product.setQuantity(form.getQuantity());
        product.setNutriScore(form.getNutriScore());
        product.setNovaGroup(form.getNovaGroup());
        product.setCategories(categoryService.findAllByIdIn(form.getCategoriesId()));
        product.setBrands(brandService.findAllByIds(form.getBrandsId()));
        product.setAllergens(allergenService.findAllByIds(form.getAllergensId()));
        product.setIngredients(productIngredientService.findAllByIds(form.getIngredientsId()));
        product.setIngredientAnalysis(productIngredientAnalysisMapper.map(form.getIngredientAnalysis()));
        product.setNutriment(nutrimentMapper.map(form.getNutriment()));
        product.setCountries(countryService.findAllByIds(form.getCountriesId()));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = findById(id);

        productRepository.delete(product);
    }

    public Product findByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with barcode: [%s] not found".formatted(barcode)));
    }

    public Page<Product> findAll(String name, String quantity, List<Integer> nutriScore, List<Integer> novaGroups, List<Long> category,
                                 List<Long> allergens, List<Long> country, boolean hasPhotos, Pageable pageable) {
        Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (quantity != null) {
                predicates.add(cb.equal(root.get("quantity"), quantity));
            }
            if (nutriScore != null && !nutriScore.isEmpty()) {
                predicates.add(root.get("nutriScore").in(nutriScore));
            }
            if (novaGroups != null && !novaGroups.isEmpty()) {
                predicates.add(root.get("novaGroup").in(novaGroups));
            }
            if (category != null && !category.isEmpty()) {
                predicates.add(root.join("categories").get("id").in(category));
            }
            if (allergens != null && !allergens.isEmpty()) {
                var allergenSubquery = query.subquery(Long.class);
                var allergenRoot = allergenSubquery.from(Product.class);
                allergenSubquery.select(allergenRoot.get("id"));
                allergenSubquery.where(allergenRoot.join("allergens").get("id").in(allergens));
                predicates.add(cb.not(root.get("id").in(allergenSubquery)));
            }
            if (country != null && !country.isEmpty()) {
                predicates.add(root.join("countries").get("id").in(country));
            }

            if(hasPhotos) {
                predicates.add(cb.isNotEmpty(root.get("images")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productRepository.findAll(spec, pageable);
    }
}
