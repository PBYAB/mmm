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
import pl.edu.pb.wi.mmm.dto.AllergenDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateAllergenRequest;
import pl.edu.pb.wi.mmm.dto.mapper.AllergenMapper;
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
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<Void> createAllergen(
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
    public ResponseEntity<Page<AllergenDTO>> listAllergens(
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

        Page<Allergen> allergens = allergenService.findAll(pageable);

        return ResponseEntity.ok(allergens.map(allergenMapper::map));
    }


    @GetMapping(ALLERGEN)
    @Operation(summary = "Find a allergen by ID")
    public ResponseEntity<AllergenDTO> findAllergenById(
            @PathVariable Long id
    ) {
        Allergen allergen = allergenService.findById(id);

        return ResponseEntity.ok(allergenMapper.map(allergen));
    }

    @DeleteMapping(ALLERGEN)
    @Operation(summary = "Delete allergen by ID")
    @ApiResponses(value = @ApiResponse(responseCode = "204"))
    public ResponseEntity<Void> deleteAllergenById(
            @PathVariable Long id
    ) {
        allergenService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(ALLERGEN)
    @Operation(summary = "Update allergen by ID")
    public ResponseEntity<Void> updateAllergenById(
            @PathVariable Long id,
            @Valid @RequestBody CreateAllergenRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);

        allergenService.updateById(id, form);

        return ResponseEntity.ok().build();
    }
}