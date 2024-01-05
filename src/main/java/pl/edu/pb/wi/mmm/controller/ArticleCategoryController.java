package pl.edu.pb.wi.mmm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.ArticleCategoryDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateArticleCategoryRequest;
import pl.edu.pb.wi.mmm.dto.mapper.ArticleCategoryMapper;
import pl.edu.pb.wi.mmm.entity.ArticleCategory;
import pl.edu.pb.wi.mmm.service.ArticleCategoryService;

import java.net.URI;

@Tag(name = "ArticleCategory", description = "Article Category APIs")
@RestController
@RequestMapping(ArticleCategoryController.API_CATEGORIES)
@RequiredArgsConstructor
public class ArticleCategoryController {

    public static final String API_CATEGORIES = "/api/v1/knowledgeBase/categories";
    public static final String CATEGORY = "/{id}";

    private final ArticleCategoryService articleCategoryService;

    private final ArticleCategoryMapper articleCategoryMapper;

    private final ValidationHandler validationHandler;

    @PostMapping
    @Operation(summary = "Create a new category")
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<Void> createCategory(
            @Valid @RequestBody CreateArticleCategoryRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);

        var saved = articleCategoryService.save(form);

        return ResponseEntity
                .created(URI.create(API_CATEGORIES + "/%s".formatted(saved.getId())))
                .build();
    }

    @GetMapping
    @Operation(summary = "List all categories with pagination")
    public ResponseEntity<Page<ArticleCategoryDTO>> listCategories(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
        );

        Page<ArticleCategory> categories = articleCategoryService.findAll(pageable);

        return ResponseEntity.ok(categories.map(articleCategoryMapper::map));
    }

    @GetMapping(CATEGORY)
    @Operation(summary = "Find a category by ID")
    public ResponseEntity<ArticleCategoryDTO> findCategoryById(
            @PathVariable Long id
    ) {
        ArticleCategory category = articleCategoryService.findById(id);

        return ResponseEntity.ok(articleCategoryMapper.map(category));
    }

    @PutMapping(CATEGORY)
    @Operation(summary = "Update category")
    public ResponseEntity<Void> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CreateArticleCategoryRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);

        articleCategoryService.update(id, form);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(CATEGORY)
    @Operation(summary = "Delete category")
    @ApiResponses(value = @ApiResponse(responseCode = "204"))
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id
    ) {
        articleCategoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
