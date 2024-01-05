package pl.edu.pb.wi.mmm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.ProductDTO;
import pl.edu.pb.wi.mmm.dto.ProductToListDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateProductRequest;
import pl.edu.pb.wi.mmm.dto.mapper.ProductMapper;
import pl.edu.pb.wi.mmm.entity.Product;
import pl.edu.pb.wi.mmm.service.ProductService;

import java.net.URI;
import java.util.List;


@Tag(name = "Product", description = "Product APIs")
@RestController
@RequestMapping(ProductController.API_PRODUCTS)
@RequiredArgsConstructor
public class ProductController {

    public static final String API_PRODUCTS = "/api/v1/products";

    private final ProductService productsService;

    private final ValidationHandler validationHandler;

    private final ProductMapper productMapper;

    @PostMapping()
    @Operation(summary = "Create a new product")
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<Void> createProduct(
            @Valid @RequestBody CreateProductRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        Product product = productsService.save(form);

        return ResponseEntity
                .created(URI.create(API_PRODUCTS + "/" + product.getId()))
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable Long id
    ) {
        var product = productsService.findById(id);

        return ResponseEntity.ok(productMapper.map(product));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody CreateProductRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        productsService.updateProduct(id, form);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    @ApiResponses(value = @ApiResponse(responseCode = "204"))
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id
    ) {
        productsService.deleteProduct(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<Page<ProductToListDTO>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) List<Integer> nutriScore,
            @RequestParam(required = false) List<Integer> novaGroups,
            @RequestParam(required = false) List<Long> category,
            @RequestParam(required = false) List<Long> allergens,
            @RequestParam(required = false) List<Long> country,
            Pageable pageable
    ) {
        return ResponseEntity.ok(productsService.
                findAll(name, quantity, nutriScore, novaGroups, category, allergens, country, pageable)
                .map(productMapper::mapToListElement)
        );
    }

    @GetMapping("/barcode/{barcode}")
    @Operation(summary = "Get a product by barcode")
    public ResponseEntity<ProductDTO> getProductByBarcode(
            @PathVariable String barcode
    ) {
        var product = productsService.findByBarcode(barcode);

        return ResponseEntity.ok(productMapper.map(product));
    }

}
