package pl.edu.pb.wi.mmm.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pb.wi.mmm.dto.ProductDTO;
import pl.edu.pb.wi.mmm.dto.ProductToListDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateProductRequest;
import pl.edu.pb.wi.mmm.entity.Product;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ProductMapperImpl implements ProductMapper {

    private final ProductCategoryMapper productCategoryMapper;

    private final AllergenMapper allergenMapper;

    private final ProductIngredientMapper productIngredientMapper;

    private final NutrimentMapper nutrimentMapper;

    private final BrandMapper brandMapper;

    private final CountryMapper countryMapper;

    private final ProductIngredientAnalysisMapper productIngredientAnalysisMapper;

    @Override
    public ProductDTO map(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .barcode(product.getBarcode())
                .quantity(product.getQuantity())
                .nutriScore(product.getNutriScore())
                .novaGroup(product.getNovaGroup())
                .brands(product.getBrands().stream().map(brandMapper::map).collect(Collectors.toSet()))
                .categories(product.getCategories().stream().map(productCategoryMapper::map).collect(Collectors.toSet()))
                .allergens(product.getAllergens().stream().map(allergenMapper::map).collect(Collectors.toSet()))
                .ingredients(product.getIngredients().stream().map(productIngredientMapper::map).collect(Collectors.toSet()))
                .ingredientAnalysis(productIngredientAnalysisMapper.map(product.getIngredientAnalysis()))
                .nutriment(nutrimentMapper.map(product.getNutriment()))
                .countries(product.getCountries().stream().map(countryMapper::map).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Product map(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .barcode(productDTO.getBarcode())
                .quantity(productDTO.getQuantity())
                .nutriScore(productDTO.getNutriScore())
                .novaGroup(productDTO.getNovaGroup())
                .brands(productDTO.getBrands().stream().map(brandMapper::map).collect(Collectors.toSet()))
                .categories(productDTO.getCategories().stream().map(productCategoryMapper::map).collect(Collectors.toSet()))
                .allergens(productDTO.getAllergens().stream().map(allergenMapper::map).collect(Collectors.toSet()))
                .ingredients(productDTO.getIngredients().stream().map(productIngredientMapper::map).collect(Collectors.toSet()))
                .ingredientAnalysis(productIngredientAnalysisMapper.map(productDTO.getIngredientAnalysis()))
                .nutriment(nutrimentMapper.map(productDTO.getNutriment()))
                .countries(productDTO.getCountries().stream().map(countryMapper::map).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public ProductToListDTO mapToListElement(Product product) {
        return ProductToListDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .barcode(product.getBarcode())
                .quantity(product.getQuantity())
                .nutriScore(product.getNutriScore())
                .novaGroup(product.getNovaGroup())
                .build();
    }

    @Override
    public Product map(CreateProductRequest createProductRequest) {
        return Product.builder()
                .name(createProductRequest.getName())
                .barcode(createProductRequest.getBarcode())
                .quantity(createProductRequest.getQuantity())
                .nutriScore(createProductRequest.getNutriScore())
                .novaGroup(createProductRequest.getNovaGroup())
                .brands(createProductRequest.getBrands().stream().map(brandMapper::map).collect(Collectors.toSet()))
                .categories(createProductRequest.getCategories().stream().map(productCategoryMapper::map).collect(Collectors.toSet()))
                .allergens(createProductRequest.getAllergens().stream().map(allergenMapper::map).collect(Collectors.toSet()))
                .ingredients(createProductRequest.getIngredients().stream().map(productIngredientMapper::map).collect(Collectors.toSet()))
                .ingredientAnalysis(productIngredientAnalysisMapper.map(createProductRequest.getIngredientAnalysis()))
                .nutriment(nutrimentMapper.map(createProductRequest.getNutriment()))
                .countries(createProductRequest.getCountries().stream().map(countryMapper::map).collect(Collectors.toSet()))
                .build();
    }

}
