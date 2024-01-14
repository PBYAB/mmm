package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import pl.edu.pb.wi.mmm.entity.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class UserDto {

    @NotNull
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private Integer age;

    private String login;

    @NotBlank
    private String email;

    @NotNull
    private Boolean enabled;

    private Set<Role> roles;
}
