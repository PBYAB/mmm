package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.CountryDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateCountryRequest;
import pl.edu.pb.wi.mmm.entity.Country;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDTO map(Country country);

    Country map(CountryDTO countryDTO);

    Country map(CreateCountryRequest createCountryRequest);
}