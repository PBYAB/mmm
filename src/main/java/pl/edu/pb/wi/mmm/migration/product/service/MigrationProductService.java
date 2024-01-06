package pl.edu.pb.wi.mmm.migration.product.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pb.wi.mmm.entity.Allergen;
import pl.edu.pb.wi.mmm.entity.Brand;
import pl.edu.pb.wi.mmm.entity.Category;
import pl.edu.pb.wi.mmm.entity.Country;
import pl.edu.pb.wi.mmm.entity.Nutriment;
import pl.edu.pb.wi.mmm.entity.ProductIngredient;
import pl.edu.pb.wi.mmm.migration.product.entity.Product;
import pl.edu.pb.wi.mmm.migration.product.repository.MigrationProductRepository;
import pl.edu.pb.wi.mmm.repository.AllergenRepository;
import pl.edu.pb.wi.mmm.repository.BrandRepository;
import pl.edu.pb.wi.mmm.repository.CategoryRepository;
import pl.edu.pb.wi.mmm.repository.CountryRepository;
import pl.edu.pb.wi.mmm.repository.NutrimentRepository;
import pl.edu.pb.wi.mmm.repository.ProductIngredientAnalysisRepository;
import pl.edu.pb.wi.mmm.repository.ProductIngredientRepository;
import pl.edu.pb.wi.mmm.repository.ProductRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MigrationProductService {

    private final MigrationProductRepository mongoMigrationProductRepository;

    private final CategoryRepository productCategoryRepository;

    private final BrandRepository brandRepository;

    private final AllergenRepository allergenRepository;

    private final ProductIngredientRepository ingredientRepository;

    private final ProductRepository productRepository;

    private final CountryRepository countryRepository;

    private final NutrimentRepository nutrimentRepository;

    private final ProductIngredientAnalysisRepository productIngredientAnalysisRepository;

    private Set<Country> countriesCache = new HashSet<>();

    @Transactional
    public pl.edu.pb.wi.mmm.entity.Product findById(String id) {
        var found = productRepository.findByBarcode(id);

        return found.orElseGet(() -> mongoMigrationProductRepository.findById(id)
                .map(this::map)
                .map(productRepository::save)
                .orElseThrow(EntityNotFoundException::new));
    }

    public Long migrateDataInBatches(int batchSize, int pagesToMigrate) {
        var currentSqlProductsSize = productRepository.count();
        long migrated = 0L;
        int pageNumber = (int) (currentSqlProductsSize / batchSize);

       for(int i = 0; i < pagesToMigrate; i++) {
            var pageRequest = PageRequest.of(pageNumber, batchSize);
            var dataBatch = mongoMigrationProductRepository.findAllByPoland(pageRequest);
            var mappedDataBatch = dataBatch.stream()
                    .map(this::map)
                    .filter(Objects::nonNull)
                    .toList();

            productRepository.saveAll(mappedDataBatch);

            migrated = migrated + mappedDataBatch.size();

            pageNumber++;
        }

        return migrated;
    }


    private pl.edu.pb.wi.mmm.entity.Product map(Product p) {
        try {
            if (productRepository.findByBarcode(p.getCode()).isPresent()) {
                return null;
            }
            var brands = saveBrands(p);

            var countries = saveCountries(p);

            var categories = saveCategories(p);

            var allergens = saveAllergens(p);

            var ingredients = saveIngredients(p);

            var nutriments = Nutriment.builder()
                    .energyKcalPer100g(p.getNutriments().getEnergyKcalPer100g())
                    .sodiumPer100g(p.getNutriments().getSodiumPer100g())
                    .fatPer100g(p.getNutriments().getFatPer100g())
                    .fiberPer100g(p.getNutriments().getFiberPer100g())
                    .proteinsPer100g(p.getNutriments().getProteinPer100g())
                    .sugarsPer100g(p.getNutriments().getSugarPer100g())
                    .saltPer100g(p.getNutriments().getSaltPer100g())
                    .build();


            var cleanTages = Optional.ofNullable(p.getIngredients_analysis_tags()).orElseGet(Set::of).stream()
                    .map(tag -> tag.split(":"))
                    .map(split -> split[1])
                    .collect(Collectors.toSet());

            Boolean hasPalmOil = null;

            if (cleanTages.contains("palm-oil")) {
                hasPalmOil = true;
            } else if (cleanTages.contains("palm-oil-free")) {
                hasPalmOil = false;
            }

            Boolean isVegan = null;

            if (cleanTages.contains("vegan")) {
                isVegan = true;
            } else if (cleanTages.contains("non-vegan")) {
                isVegan = false;
            }

            Boolean isVegetarian = null;

            if (cleanTages.contains("vegetarian")) {
                isVegetarian = true;
            } else if (cleanTages.contains("non-vegetarian")) {
                isVegetarian = false;
            }


            var productIngredientAnalysis =
                    pl.edu.pb.wi.mmm.entity.ProductIngredientAnalysis.builder()
                            .fromPalmOil(hasPalmOil)
                            .vegan(isVegan)
                            .vegetarian(isVegetarian)
                            .ingredientsDescription(p.getIngredients_text())
                            .build();


            return pl.edu.pb.wi.mmm.entity.Product.builder()
                    .name(p.getProduct_name())
                    .barcode(p.getCode())
                    .quantity(p.getQuantity())
                    .novaGroup(Optional.ofNullable((p.getNova_group())).map(Integer::parseInt).orElse(null))
                    .nutriScore(Optional.ofNullable((p.getNutriScoreGrade())).map(Integer::parseInt).orElse(null))
                    .brands(brands)
                    .categories(categories)
                    //.images(null)
                    .allergens(allergens)
                    .ingredients(ingredients)
                    .nutriment(nutriments)
                    .ingredientAnalysis(productIngredientAnalysis)
                    .countries(countries)
                    .build();
        } catch (Exception e) {
            log.error("Error while mapping product: {}", p, e);
            return null;
        }
    }

    public Set<ProductIngredient> saveIngredients(Product product) {
        var mongoingredients = Optional.ofNullable(product.getIngredients()).orElseGet(Set::of);

        Set<String> ingredientTags = mongoingredients.stream()
                .map(pl.edu.pb.wi.mmm.migration.product.entity.Ingredient::getTagName).collect(Collectors.toSet());


        Set<ProductIngredient> categories = ingredientRepository.findAllByNameIn(
                ingredientTags.stream()
                        .map(tag -> tag.split(":")[1].trim())
                        .map(countryName -> {
                            String[] parts = countryName.split("-");
                            if (parts.length > 1) {
                                return Arrays.stream(parts)
                                        .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1))
                                        .collect(Collectors.joining(" "));
                            }
                            return countryName.substring(0, 1).toUpperCase(Locale.ROOT) + countryName.substring(1);
                        })
                        .collect(Collectors.toSet())
        );

        Set<String> existingIngredientNames = categories.stream()
                .map(ProductIngredient::getName)
                .collect(Collectors.toSet());

        Set<ProductIngredient> newCategories = mongoingredients.stream()
                .filter(whatever -> {
                    String[] parts = whatever.getTagName().split(":")[1].split("-");
                    String normalizedName = parts.length > 1 ?
                            Arrays.stream(parts)
                                    .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1))
                                    .collect(Collectors.joining(" "))
                            : whatever.getTagName().split(":")[1].substring(0, 1).toUpperCase(Locale.ROOT) + whatever.getTagName().split(":")[1].substring(1);
                   return !existingIngredientNames.contains(normalizedName);
                })
                .map(whatever -> {
                    String[] parts = whatever.getTagName().split(":")[1].split("-");
                    String normalizedName = parts.length > 1 ?
                            Arrays.stream(parts)
                                    .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1))
                                    .collect(Collectors.joining(" "))
                            : whatever.getTagName().split(":")[1].substring(0, 1).toUpperCase(Locale.ROOT) + whatever.getTagName().split(":")[1].substring(1);
                    return ingredientRepository.findByName(normalizedName).orElseGet(() -> {
                        var isVegan = mapToBoolean(whatever.getVegan());
                        var isVegetarian = mapToBoolean(whatever.getVegetarian());
                        var isFromPalmOil = mapToBoolean(whatever.getFrom_palm_oil());

                        return ingredientRepository.save(
                                ProductIngredient.builder()
                                        .name(normalizedName)
                                        .vegan(isVegan)
                                        .vegetarian(isVegetarian)
                                        .fromPalmOil(isFromPalmOil)
                                        .build()
                        );
                    });
                })
                .collect(Collectors.toSet());

        ingredientRepository.saveAll(newCategories);

        categories.addAll(newCategories);

        return categories;
    }

    private Boolean mapToBoolean(String value) {
        return switch (Optional.ofNullable(value).orElse("")) {
            case "yes" -> true;
            case "no" -> false;
            default -> null;
        };
    }

    public Set<Allergen> saveAllergens(Product product) {
        Set<String> allergenNames = product.getAllergensTags().stream()
                .map(tag -> tag.split(":")[1])
                .collect(Collectors.toSet());

        Set<Allergen> existingAllergens = allergenRepository.findAllByNameIn(allergenNames);
        Set<String> existingAllergenNames = existingAllergens.stream().map(Allergen::getName).collect(Collectors.toSet());

        Set<Allergen> newAllergens = allergenNames.stream()
                .filter(allergenName -> !existingAllergenNames.contains(allergenName))
                .map(allergenName -> Allergen.builder().name(allergenName).build())
                .collect(Collectors.toSet());

        Set<Allergen> savedNewAllergens = new HashSet<>(allergenRepository.saveAll(newAllergens));

        Set<Allergen> allAllergens = new HashSet<>(existingAllergens);
        allAllergens.addAll(savedNewAllergens);

        return allAllergens;
    }


    public Set<Category> saveCategories(Product product) {
        Set<String> categoryTags = Optional.ofNullable(product.getCategoriesTags()).orElseGet(Set::of);


        Set<Category> categories = productCategoryRepository.findAllByNameIn(
                categoryTags.stream()
                        .map(tag -> tag.split(":")[1].trim())
                        .map(countryName -> {
                            String[] parts = countryName.split("-");
                            if (parts.length > 1) {
                                return Arrays.stream(parts)
                                        .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1))
                                        .collect(Collectors.joining(" "));
                            }
                            return countryName.substring(0, 1).toUpperCase(Locale.ROOT) + countryName.substring(1);
                        })
                        .collect(Collectors.toSet())
        );

        Set<String> existingCategoryNames = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        Set<Category> newCategories = categoryTags.stream()
                .map(tag -> tag.split(":")[1].trim())
                .map(countryName -> {
                    String[] parts = countryName.split("-");
                    if (parts.length > 1) {
                        return Arrays.stream(parts)
                                .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1))
                                .collect(Collectors.joining(" "));
                    }
                    return countryName.substring(0, 1).toUpperCase(Locale.ROOT) + countryName.substring(1);
                })
                .filter(categoryName -> !existingCategoryNames.contains(categoryName))
                .map(categoryName -> Category.builder().name(categoryName).build())
                .collect(Collectors.toSet());

        productCategoryRepository.saveAll(newCategories);

        categories.addAll(newCategories);

        return categories;
    }


    private Set<Country> saveCountries(Product product) {
        Set<String> countryTags = Optional.ofNullable(product.getCountries_tags()).orElseGet(Set::of);

        Set<Country> countries = countryRepository.findAllByNameIn(
                countryTags.stream()
                        .map(tag -> tag.split(":")[1].trim())
                        .map(countryName -> {
                            String[] parts = countryName.split("-");
                            if (parts.length > 1) {
                                return Arrays.stream(parts)
                                        .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1))
                                        .collect(Collectors.joining(" "));
                            }
                            return countryName.substring(0, 1).toUpperCase(Locale.ROOT) + countryName.substring(1);
                        })
                        .collect(Collectors.toSet())
        );

        Set<String> existingCountryNames = countries.stream()
                .map(Country::getName)
                .collect(Collectors.toSet());

        Set<Country> newCountries = countryTags.stream()
                .map(tag -> tag.split(":")[1].trim())
                .map(countryName -> {
                    String[] parts = countryName.split("-");
                    if (parts.length > 1) {
                        return Arrays.stream(parts)
                                .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1))
                                .collect(Collectors.joining(" "));
                    }
                    return countryName.substring(0, 1).toUpperCase(Locale.ROOT) + countryName.substring(1);
                })
                .filter(countryName -> !existingCountryNames.contains(countryName))
                .map(countryName -> Country.builder().name(countryName).build())
                .collect(Collectors.toSet());

        countryRepository.saveAll(newCountries);

        countries.addAll(newCountries);

        return countries;
    }

    public Set<Brand> saveBrands(Product product) {
        Set<String> brandTags = Optional.ofNullable(product.getBrands()).orElseGet(Set::of);

        var normalizedBransNames = brandTags.stream()
                .map(String::trim)
                .map(name -> {
                    String[] parts = name.split("-");
                    if (parts.length > 1) {
                        return Arrays.stream(parts)
                                .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1))
                                .collect(Collectors.joining(" "));
                    }
                    return name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1);
                })
                .collect(Collectors.toSet());

        Set<Brand> brands = brandRepository.findAllByNameIn(
             normalizedBransNames
        );

        Set<String> existingBrandNames = brands.stream()
                .map(Brand::getName)
                .collect(Collectors.toSet());

        Set<Brand> newCountries = brandTags.stream()
                .map(countryName -> {
                    String[] parts = countryName.split("-");
                    if (parts.length > 1) {
                        return Arrays.stream(parts)
                                .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1))
                                .collect(Collectors.joining(" "));
                    }
                    return countryName.substring(0, 1).toUpperCase(Locale.ROOT) + countryName.substring(1);
                })
                .filter(name -> !existingBrandNames.contains(name))
                .map(name -> Brand.builder().name(name).build())
                .collect(Collectors.toSet());

        brandRepository.saveAll(newCountries);


        brands.addAll(newCountries);

        return brands;
    }
}
