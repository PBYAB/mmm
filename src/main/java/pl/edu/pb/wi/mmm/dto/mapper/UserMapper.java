package pl.edu.pb.wi.mmm.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pb.wi.mmm.dto.UserDTO;
import pl.edu.pb.wi.mmm.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "fullName", expression = "java(user.getFirstName() + ' ' + user.getLastName())")
    UserDTO map(User user);

    User map(UserDTO userDto);
}
