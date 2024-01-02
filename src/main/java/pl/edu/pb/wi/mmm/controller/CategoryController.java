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
import pl.edu.pb.wi.mmm.dto.CategoryDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateProductCategoryRequest;
import pl.edu.pb.wi.mmm.dto.mapper.CategoryMapper;
import pl.edu.pb.wi.mmm.entity.Category;
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
    public ResponseEntity<Page<CategoryDTO>> getCategories(
            Pageable pageable
    ) {
        return ResponseEntity.ok(categoryService.findAll(pageable).map(categoryMapper::map));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by ID")
    public ResponseEntity<CategoryDTO> getCategory(
            @PathVariable Long id
    ) {
        var category = categoryService.findById(id);

        return ResponseEntity.ok(categoryMapper.map(category));
    }


    @PostMapping
    @Operation(summary = "Create a new category")
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
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
    @Operation(summary = "Update an existing category")
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
    @ApiResponses(value = @ApiResponse(responseCode = "204"))
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id
    ) {
        categoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }
}
