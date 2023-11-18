package pl.edu.pb.wi.mmm.controller.migration;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.mmm.entity.Product;
import pl.edu.pb.wi.mmm.service.migration.MigrationProductService;

import java.util.List;

@Tag(name = "Product", description = "Products")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final MigrationProductService migrationProductService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(
            @PathVariable String id
    ) {
        Product byId = migrationProductService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping("/migrate/{batchSize}/{pagesToMigrate}")
    public ResponseEntity<Long> migrate(
            @PathVariable int batchSize,
            @PathVariable int pagesToMigrate
    ) {
        var quantity = migrationProductService.migrateDataInBatches(batchSize, pagesToMigrate);
        return ResponseEntity.ok(quantity);
    }
}
