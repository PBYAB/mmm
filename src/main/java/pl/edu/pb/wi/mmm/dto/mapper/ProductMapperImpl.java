package pl.edu.pb.wi.mmm.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pb.wi.mmm.dto.ProductDTO;
import pl.edu.pb.wi.mmm.dto.ProductToListDTO;
import pl.edu.pb.wi.mmm.entity.Product;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ProductMapperImpl implements ProductMapper {

    private final CategoryMapper categoryMapper;

    private final AllergenMapper allergenMapper;

    private final ProductIngredientMapper productIngredientMapper;

    private final NutrimentMapper nutrimentMapper;

    private final BrandMapper brandMapper;

    private final CountryMapper countryMapper;

    private final ProductIngredientAnalysisMapper productIngredientAnalysisMapper;

    private final ProductImageMapper productImageMapper;

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
                .categories(product.getCategories().stream().map(categoryMapper::map).collect(Collectors.toSet()))
                .allergens(product.getAllergens().stream().map(allergenMapper::map).collect(Collectors.toSet()))
                .ingredients(product.getIngredients().stream().map(productIngredientMapper::map).collect(Collectors.toSet()))
                .ingredientAnalysis(productIngredientAnalysisMapper.map(product.getIngredientAnalysis()))
                .nutriment(nutrimentMapper.map(product.getNutriment()))
                .countries(product.getCountries().stream().map(countryMapper::map).collect(Collectors.toSet()))
                .images(product.getImages().stream().map(productImageMapper::map).collect(Collectors.toSet()))
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
                .categories(productDTO.getCategories().stream().map(categoryMapper::map).collect(Collectors.toSet()))
                .allergens(productDTO.getAllergens().stream().map(allergenMapper::map).collect(Collectors.toSet()))
                .ingredients(productDTO.getIngredients().stream().map(productIngredientMapper::map).collect(Collectors.toSet()))
                .ingredientAnalysis(productIngredientAnalysisMapper.map(productDTO.getIngredientAnalysis()))
                .nutriment(nutrimentMapper.map(productDTO.getNutriment()))
                .countries(productDTO.getCountries().stream().map(countryMapper::map).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public ProductToListDTO mapToListElement(Product product) {
        ProductToListDTO productToListDTO = ProductToListDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .barcode(product.getBarcode())
                .quantity(product.getQuantity())
                .nutriScore(product.getNutriScore())
                .novaGroup(product.getNovaGroup())
                .build();

        if (!product.getImages().isEmpty()) {
            productToListDTO.setImage(productImageMapper.map(product.getImages().stream().findFirst().get()));
        }

        return productToListDTO;
    }
}
