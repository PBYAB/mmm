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
import pl.edu.pb.wi.mmm.dto.ProductIngredientDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateProductIngredientRequest;
import pl.edu.pb.wi.mmm.dto.mapper.ProductIngredientMapper;
import pl.edu.pb.wi.mmm.entity.ProductIngredient;
import pl.edu.pb.wi.mmm.service.ProductIngredientService;

import java.net.URI;

@Tag(name = "ProductIngredient", description = "ProductIngredient APIs")
@RestController
@RequestMapping(ProductIngredientController.API_PRODUCT_INGREDIENT)
@RequiredArgsConstructor
public class ProductIngredientController {

    public static final String API_PRODUCT_INGREDIENT = "/api/v1/products/ingredients";

    private final ValidationHandler validationHandler;

    private final ProductIngredientMapper productIngredientMapper;

    private final ProductIngredientService productIngredientService;

    @GetMapping
    @Operation(summary = "Get a list of all product ingredients")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ProductIngredient.class)
                            )
                    }
            )
    })
    public ResponseEntity<Page<ProductIngredientDTO>> getProductIngredients(
            Pageable pageable
    ) {
        return ResponseEntity.ok(productIngredientService.findAll(pageable).map(productIngredientMapper::map));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product ingredient by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ProductIngredient.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product ingredient not found"
            )
    })
    public ResponseEntity<ProductIngredientDTO> getProductIngredient(
            @PathVariable Long id
    ) {
        var productIngredient = productIngredientService.findById(id);
        return ResponseEntity.ok(productIngredientMapper.map(productIngredient));
    }


    @PostMapping
    @Operation(summary = "Create a new product ingredient")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product ingredient created successfully",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ProductIngredient.class)
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
            )
    })
    public ResponseEntity<ProductIngredient> createProductIngredient(
            @Valid @org.springframework.web.bind.annotation.RequestBody CreateProductIngredientRequest productIngredient,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        ProductIngredient createdProductIngredient = productIngredientService.createProductIngredient(productIngredient);
        return ResponseEntity
                .created(URI.create(API_PRODUCT_INGREDIENT + "/" + createdProductIngredient.getId()))
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing country")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Country updated successfully"
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
                    description = "Country not found"
            )
    })
    public ResponseEntity<?> updateProductIngredient(
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody CreateProductIngredientRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        productIngredientService.updateProductIngredient(id, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing category")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Country deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access denied"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found"
            )
    })
    public ResponseEntity<?> deleteProductIngredient(
            @PathVariable Long id
    ) {
        productIngredientService.deleteProductIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
