package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import pl.edu.pb.wi.mmm.dto.NutrimentDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateNutrimentRequest;
import pl.edu.pb.wi.mmm.entity.Nutriment;

@Mapper(componentModel = "spring")
public interface NutrimentMapper {

    NutrimentDTO map(Nutriment nutriment);

    Nutriment map(NutrimentDTO nutrimentDTO);

    Nutriment map(CreateNutrimentRequest createNutrimentRequest);
}
