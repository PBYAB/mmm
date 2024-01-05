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
import pl.edu.pb.wi.mmm.dto.ArticleDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateArticleRequest;
import pl.edu.pb.wi.mmm.dto.mapper.ArticleMapper;
import pl.edu.pb.wi.mmm.entity.Article;
import pl.edu.pb.wi.mmm.service.ArticleService;

import java.net.URI;

@Tag(name = "Article", description = "Article APIs")
@RestController
@RequestMapping(ArticleController.API_ARTICLES)
@RequiredArgsConstructor
public class ArticleController {

    public static final String API_ARTICLES = "/api/v1/knowledgeBase/articles";

    public static final String ARTICLE = "/{id}";

    private final ArticleService articleService;

    private final ArticleMapper articleMapper;

    private final ValidationHandler validationHandler;

    @PostMapping()
    @Operation(summary = "Create a new article")
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<Void> createArticle(
            @Valid @RequestBody CreateArticleRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);

        var saved = articleService.save(form);

        return ResponseEntity
                .created(URI.create(API_ARTICLES + "/%s".formatted(saved.getId())))
                .build();
    }

    @GetMapping
    @Operation(summary = "List all articles with pagination")
    public ResponseEntity<Page<ArticleDTO>> listCategories(
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

        return ResponseEntity.ok(articleService.findAll(pageable)
                .map(articleMapper::map));
    }

    @GetMapping(ARTICLE)
    @Operation(summary = "Find an article by ID")
    public ResponseEntity<ArticleDTO> findArticleById(
            @PathVariable Long id
    ) {
        Article article = articleService.findById(id);

        return ResponseEntity.ok(articleMapper.map(article));
    }

    @PutMapping(ARTICLE)
    @Operation(summary = "Update an article")
    public ResponseEntity<Void> updateArticleById(
            @PathVariable Long id,
            @Valid @RequestBody CreateArticleRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);

        articleService.update(id, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(ARTICLE)
    @Operation(summary = "Delete an article")
    @ApiResponses(value = @ApiResponse(responseCode = "204"))
    public ResponseEntity<Void> deleteArticleById(
            @PathVariable Long id
    ) {
        articleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
