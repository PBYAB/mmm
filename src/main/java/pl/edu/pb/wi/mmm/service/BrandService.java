package pl.edu.pb.wi.mmm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.create.CreateBrandRequest;
import pl.edu.pb.wi.mmm.entity.Brand;
import pl.edu.pb.wi.mmm.repository.BrandRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Transactional
    public Brand createBrand(CreateBrandRequest form) {
        var brand = Brand.builder()
                .name(form.getName())
                .build();

        return brandRepository.save(brand);
    }

    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand with ID: [%s] not found".formatted(id)));
    }

    @Transactional
    public void deleteBrandById(Long id) {
        var brand = findById(id);

        brandRepository.delete(brand);
    }

    @Transactional
    public void updateBrandById(Long id, CreateBrandRequest form) {
        var brand = findById(id);
        brand.setName(form.getName());
    }
}
