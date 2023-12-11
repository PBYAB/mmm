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
import pl.edu.pb.wi.mmm.dto.CategoryDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateProductCategoryRequest;
import pl.edu.pb.wi.mmm.dto.mapper.CategoryMapper;
import pl.edu.pb.wi.mmm.entity.Category;
import pl.edu.pb.wi.mmm.entity.Country;
import pl.edu.pb.wi.mmm.service.CategoryService;

import java.net.URI;

@Tag(name = "Category", description = "Category APIs")
@RestController
@RequestMapping(CategoryController.API_CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
    public static final String API_CATEGORY = "/api/v1/categories";

    private final ValidationHandler validationHandler;

    private final CategoryMapper categoryMapper;

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Category.class)
                            )
                    }
            )
    })
    public ResponseEntity<Page<CategoryDTO>> getCategories(
            Pageable pageable
    ) {
        return ResponseEntity.ok(categoryService.findAll(pageable).map(categoryMapper::map));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Category.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found"
            )
    })
    public ResponseEntity<CategoryDTO> getCategory(
            @PathVariable Long id
    ) {
        var category = categoryService.findById(id);

        return ResponseEntity.ok(categoryMapper.map(category));
    }


    @PostMapping
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Country created successfully",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Country.class)
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
    public ResponseEntity<Category> createCategory(
            @Valid @org.springframework.web.bind.annotation.RequestBody CreateProductCategoryRequest category,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        Category createdProductCategory = categoryService.createCategory(category);
        return ResponseEntity
                .created(URI.create(API_CATEGORY + "/" + createdProductCategory.getId()))
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
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody CreateProductCategoryRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        categoryService.updateProductCategory(id, form);
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
    public ResponseEntity<?> deleteCategory(
            @PathVariable Long id
    ) {
        categoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }
}
