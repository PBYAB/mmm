package pl.edu.pb.wi.mmm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.CreateCountryRequest;
import pl.edu.pb.wi.mmm.entity.Country;
import pl.edu.pb.wi.mmm.repository.CountryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public Country createCountry(CreateCountryRequest form) {
        var country = Country.builder()
                .name(form.getName())
                .build();

        return countryRepository.save(country);
    }

    public Country findByName(String name) {
        return countryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Country with name: [%s] not found".formatted(name)));
    }

    public Country findById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country with ID: [%s] not found".formatted(id)));
    }

    public Page<Country> findAll(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    public void updateCountry(Long id, CreateCountryRequest form) {
        var country = findById(id);
        country.setName(form.getName());
    }

    public void deleteCountry(Long id) {
        var country = findById(id);
        countryRepository.delete(country);
    }
}
