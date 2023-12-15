package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.edu.pb.wi.mmm.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByBarcode(String barcode);

    Page<Product> findAll(Pageable pageable);

}
