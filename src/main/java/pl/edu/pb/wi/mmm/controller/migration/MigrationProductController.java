package pl.edu.pb.wi.mmm.controller.migration;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.mmm.entity.Product;
import pl.edu.pb.wi.mmm.migration.product.repository.MigrationProductRepository;
import pl.edu.pb.wi.mmm.migration.product.service.MigrationProductService;

import java.util.Arrays;

@Tag(name = "Migration", description = "Migration API")
@RestController
@RequestMapping("/api/v1/migration/products")
@RequiredArgsConstructor
public class MigrationProductController {

    private final MigrationProductService migrationProductService;

    private final MigrationProductRepository migrationProductRepository;

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

    @GetMapping("/poland")
    public ResponseEntity<Page<pl.edu.pb.wi.mmm.migration.product.entity.Product>> findPoland(
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                migrationProductRepository.findAllByPoland(
                        pageable
                )
        );
    }
}
