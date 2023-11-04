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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.mmm.dto.ArticleDTO;
import pl.edu.pb.wi.mmm.dto.CreateArticleRequest;
import pl.edu.pb.wi.mmm.dto.mapper.ArticleMapper;
import pl.edu.pb.wi.mmm.entity.Article;
import pl.edu.pb.wi.mmm.service.ArticleService;

import java.net.URI;

@Tag(name = "knowledgeBase")
@RestController
@RequestMapping(ArticleController.API_ARTICLES)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ArticleController {

    public static final String API_ARTICLES = "/api/v1/knowledgeBase/articles";

    public static final String ARTICLE = "/{id}";

    private final ArticleService articleService;

    private final ArticleMapper articleMapper;

    private final ValidationHandler validationHandler;

    @PostMapping()
    @Operation(summary = "Create a new article")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Article created successfully"
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
    public ResponseEntity<?> createArticle(
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
    public Page<ArticleDTO> listCategories(
            Pageable pageable
    ) {
        return articleService.findAll(pageable)
                .map(articleMapper::map);
    }

    @GetMapping(ARTICLE)
    @Operation(summary = "Find an article by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ArticleDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Article with the specified ID not found"
            )
    })
    public ResponseEntity<ArticleDTO> findArticleById(
            @PathVariable Long id
    ) {
        Article article = articleService.findById(id);

        return ResponseEntity.ok(articleMapper.map(article));
    }
}
