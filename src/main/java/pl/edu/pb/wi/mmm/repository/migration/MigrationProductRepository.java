package pl.edu.pb.wi.mmm.repository.migration;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.mmm.entity.migration.Product;

import java.util.List;

@Repository
public interface MigrationProductRepository extends MongoRepository<Product, String> {

    List<Product> findTop10By();

    List<Product> findAllBy(Pageable pageable);
}
