package pl.edu.pb.wi.mmm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.AllergenDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateAllergenRequest;
import pl.edu.pb.wi.mmm.dto.mapper.AllergenMapper;
import pl.edu.pb.wi.mmm.dto.pagescheme.AllergenPageSchema;
import pl.edu.pb.wi.mmm.entity.Allergen;
import pl.edu.pb.wi.mmm.service.AllergenService;

import java.net.URI;

@Tag(name = "Allergen", description = "Allergen APIs")
@RestController
@RequestMapping(AllergenController.API_ALLERGENS)
@RequiredArgsConstructor
public class AllergenController {

    public static final String API_ALLERGENS = "/api/v1/allergens";
    public static final String ALLERGEN = "/{id}";

    private final AllergenService allergenService;

    private final AllergenMapper allergenMapper;

    private final ValidationHandler validationHandler;

    @PostMapping
    @Operation(summary = "Create a new allergen")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Allergen created successfully"
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
    public ResponseEntity<?> createAllergen(
            @Valid @RequestBody CreateAllergenRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);

        var saved = allergenService.save(form);

        return ResponseEntity
                .created(URI.create(API_ALLERGENS + "/%s".formatted(saved.getId())))
                .build();
    }


    @GetMapping
    @Operation(summary = "List all allergens with pagination")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AllergenPageSchema.class)
                            )
                    })
    })
    public ResponseEntity<?> listAllergens(
            Pageable pageable
    ) {
        return ResponseEntity.ok(allergenService.findAll(pageable).map(allergenMapper::map));
    }


    @GetMapping(ALLERGEN)
    @Operation(summary = "Find a allergen by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Allergen.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Allergen with the specified ID not found"
            )
    })
    public ResponseEntity<AllergenDTO> findAllergenById(
            @PathVariable Long id
    ) {
        Allergen allergen = allergenService.findById(id);

        return ResponseEntity.ok(allergenMapper.map(allergen));
    }

    @DeleteMapping(ALLERGEN)
    @Operation(summary = "Delete allergen by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No Content - Allergen deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Allergen with the specified ID not found"
            )
    })
    public ResponseEntity<?> deleteAllergenById(
            @PathVariable Long id
    ) {
        allergenService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(ALLERGEN)
    @Operation(summary = "Update allergen by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No Content - Allergen updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Allergen with the specified ID not found"
            )
    })
    public ResponseEntity<?> updateAllergenById(
            @PathVariable Long id,
            @Valid @RequestBody CreateAllergenRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);

        allergenService.updateById(id, form);

        return ResponseEntity.ok().build();
    }
}
