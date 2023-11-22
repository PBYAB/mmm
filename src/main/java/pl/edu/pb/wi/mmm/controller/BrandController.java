package pl.edu.pb.wi.mmm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.CreateBrandRequest;
import pl.edu.pb.wi.mmm.entity.Brand;
import pl.edu.pb.wi.mmm.service.BrandService;

import java.net.URI;

@Tag(name = "Brand", description = "Brand APIs")
@RestController
@RequestMapping(BrandController.API_BRANDS)
@RequiredArgsConstructor
public class BrandController {

    public static final String API_BRANDS = "/api/v1/brands";

    private final BrandService brandService;

    private final ValidationHandler validationHandler;


    @PostMapping
    @Operation(summary = "Create a new brand")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Brand created successfully",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Brand.class)
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
    public ResponseEntity<?> createBrand(
            @Valid @RequestBody CreateBrandRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        Brand created = brandService.createBrand(form);
        return ResponseEntity
                .created(URI.create(API_BRANDS + "/" + created.getId()))
                .build();
    }


    @GetMapping
    @Operation(summary = "Get all brands")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Page.class)
                            )
                    }
            )
    })
    public ResponseEntity<?> getAllBrands(
            Pageable pageable
    ) {
        return ResponseEntity.ok(brandService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get brand by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Brand.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found"
            )
    })
    public ResponseEntity<Brand> getBrandById(
            Long id
    ) {
        return ResponseEntity.ok(brandService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete brand by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No content - successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found"
            )
    })
    public ResponseEntity<?> deleteBrandById(
            Long id
    ) {
        brandService.deleteBrandById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update brand by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found"
            )
    })
    public ResponseEntity<?> updateBrandById(
            Long id,
            @Valid @RequestBody CreateBrandRequest form,
            BindingResult bindingResult
    ) {
        validationHandler.validateAndHandleErrors(bindingResult);
        brandService.updateBrandById(id, form);
        return ResponseEntity.ok().build();
    }
}
