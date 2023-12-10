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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.CountryDTO;
import pl.edu.pb.wi.mmm.dto.IngredientListItem;
import pl.edu.pb.wi.mmm.dto.create.CreateCountryRequest;
import pl.edu.pb.wi.mmm.dto.create.CreateIngredientRequest;
import pl.edu.pb.wi.mmm.dto.mapper.IngredientMapper;
import pl.edu.pb.wi.mmm.entity.Country;
import pl.edu.pb.wi.mmm.entity.Ingredient;
import pl.edu.pb.wi.mmm.service.IngredientService;

import java.net.URI;

@Tag(name = "Ingredient", description = "Ingredient APIs")
@RestController
@RequestMapping(IngredientController.API_INGREDIENTS)
@RequiredArgsConstructor
public class IngredientController {

        public static final String API_INGREDIENTS = "/api/v1/ingredients";

        private final IngredientService ingredientService;

        private final ValidationHandler validationHandler;

        private final IngredientMapper ingredientMapper;

    @PostMapping
    @Operation(summary = "Create a new ingredient")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created successfully",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Ingredient.class)
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
    public ResponseEntity<Ingredient> createIngredient(
            @Valid @RequestBody CreateIngredientRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        Ingredient saved = ingredientService.createAndSave(form);

        return ResponseEntity
                .created(URI.create(API_INGREDIENTS + "/" + saved.getId()))
                .build();
    }

    @GetMapping
    @Operation(summary = "List all ingredients")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Page.class)
                            )
                    }
            )
    })
    public ResponseEntity<Page<IngredientListItem>> findAll(
            Pageable pageable
    ) {
        var page = ingredientService.findAll(pageable);

        return ResponseEntity.ok(page.map(ingredientMapper::mapToListItem));
    }
}