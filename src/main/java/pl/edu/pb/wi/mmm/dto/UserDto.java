package pl.edu.pb.wi.mmm.dto;

import lombok.*;
import pl.edu.pb.wi.mmm.entity.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String login;

    private String email;

    private Boolean enabled;

    private Set<Role> roles;
}
