package pl.edu.pb.wi.mmm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import pl.edu.pb.wi.mmm.dto.pagescheme.ProductToListPageSchema;
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid input data or validation errors"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access denied"
            )
    })
    public ResponseEntity<?> createProduct(
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Product.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable Long id
    ) {
        var product = productsService.findById(id);

        return ResponseEntity.ok(productMapper.map(product));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Product.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid input data or validation errors"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access denied"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody CreateProductRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        Product product = productsService.findById(id);

        productsService.updateProduct(id, form);

        return ResponseEntity.ok(productMapper.map(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long id
    ) {
        productsService.deleteProduct(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ProductToListPageSchema.class)
                            )
                    }
            )
    })
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    public ResponseEntity<ProductDTO> getProductByBarcode(
            @PathVariable String barcode
    ) {
        var product = productsService.findByBarcode(barcode);

        return ResponseEntity.ok(productMapper.map(product));
    }

}
