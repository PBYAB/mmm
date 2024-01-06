package pl.edu.pb.wi.mmm.migration.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.mmm.migration.product.entity.Product;

import java.util.List;

@Repository
public interface MigrationProductRepository extends MongoRepository<Product, String> {

    List<Product> findTop10By();

    List<Product> findAllBy(Pageable pageable);

    @Query("{ 'countries_tags': { $in: ['en:poland'] } }")
    Page<Product> findAllByPoland(Pageable pageable);
}
