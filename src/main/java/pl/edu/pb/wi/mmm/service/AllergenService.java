package pl.edu.pb.wi.mmm.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.create.CreateAllergenRequest;
import pl.edu.pb.wi.mmm.entity.Allergen;
import pl.edu.pb.wi.mmm.repository.AllergenRepository;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AllergenService {

    private final AllergenRepository allergenRepository;

    public List<Allergen> findAll() {
        return allergenRepository.findAll();
    }

    public Allergen findById(Long id) {
        return allergenRepository.findById(id).orElseThrow(() -> new RuntimeException("Allergen not found"));
    }

    @Transactional
    public Allergen save(CreateAllergenRequest form) {
        allergenRepository.findByName(form.getName()).ifPresent(found -> {
            throw new RuntimeException("Allergen with name: [%s] already exists".formatted(found.getName()));
        });

        Allergen allergen = Allergen.builder()
                .name(form.getName())
                .build();

        return allergenRepository.save(allergen);
    }

    public Page<Allergen> findAll(Pageable pageable) {
        return allergenRepository.findAll(pageable);
    }

    @Transactional
    public void deleteById(Long id) {
        Allergen allergen = findById(id);
        allergenRepository.delete(allergen);
    }

    @Transactional
    public void updateById(Long id, CreateAllergenRequest form) {
        Allergen allergen = findById(id);
        allergen.setName(form.getName());
    }

    public Set<Allergen> findAllByIds(Set<Long> ids) {
        return allergenRepository.findAllByIdIn(ids);
    }
}
