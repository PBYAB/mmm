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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.CountryDTO;
import pl.edu.pb.wi.mmm.dto.CreateCountryRequest;
import pl.edu.pb.wi.mmm.dto.mapper.CountryMapper;
import pl.edu.pb.wi.mmm.entity.Country;
import pl.edu.pb.wi.mmm.service.CountryService;

import java.net.URI;

@Tag(name = "Country", description = "Country APIs")
@RestController
@RequestMapping(CountryController.API_COUNTRIES)
@RequiredArgsConstructor
public class CountryController {

    public static final String API_COUNTRIES = "/api/v1/countries";

    private final CountryService countryService;

    private final ValidationHandler validationHandler;

    private final CountryMapper countryMapper;


    @GetMapping
    @Operation(summary = "Get a list of all countries")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Country.class)
                            )
                    }
            )
    })
    public ResponseEntity<Page<CountryDTO>> getCountries(
            Pageable pageable
    ) {
        return ResponseEntity.ok(countryService.findAll(pageable).map(countryMapper::map));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a country by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Country.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found"
            )
    })
    public ResponseEntity<CountryDTO> getCountry(
            @PathVariable Long id
    ) {
        var country = countryService.findById(id);

        return ResponseEntity.ok(countryMapper.map(country));
    }


    @PostMapping
    @Operation(summary = "Create a new country")
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
    public ResponseEntity<Country> createCountry(
            @Valid @org.springframework.web.bind.annotation.RequestBody CreateCountryRequest country,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        Country createdCountry = countryService.createCountry(country);
        return ResponseEntity
                .created(URI.create(API_COUNTRIES + "/" + createdCountry.getId()))
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
    public ResponseEntity<?> updateCountry(
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody CreateCountryRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        countryService.updateCountry(id, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing country")
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
    public ResponseEntity<?> deleteCountry(
            @PathVariable Long id
    ) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}