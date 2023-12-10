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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.ArticleCategoryDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateArticleCategoryRequest;
import pl.edu.pb.wi.mmm.dto.mapper.ArticleCategoryMapper;
import pl.edu.pb.wi.mmm.entity.ArticleCategory;
import pl.edu.pb.wi.mmm.service.ArticleCategoryService;

import java.net.URI;

@Tag(name = "Article", description = "Article Category APIs")
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created successfully"
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
    public ResponseEntity<?> createCategory(
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Page.class)
                            )
                    })
    })
    public Page<ArticleCategoryDTO> listCategories(
            Pageable pageable
    ) {
        return articleCategoryService.findAll(pageable)
                .map(articleCategoryMapper::map);
    }

    @GetMapping(CATEGORY)
    @Operation(summary = "Find a category by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ArticleCategoryDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Category with the specified ID not found"
            )
    })
    public ResponseEntity<ArticleCategoryDTO> findCategoryById(
            @PathVariable Long id
    ) {
        ArticleCategory category = articleCategoryService.findById(id);

        return ResponseEntity.ok(articleCategoryMapper.map(category));
    }
}
