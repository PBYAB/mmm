package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Product;
import pl.edu.pb.wi.mmm.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByBarcode(String barcode);

    Page<Product> findAllByOwner(User owner, Pageable pageable);
}
