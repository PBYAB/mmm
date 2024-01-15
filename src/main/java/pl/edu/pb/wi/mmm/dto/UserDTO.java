package pl.edu.pb.wi.mmm.dto;

import jakarta.validation.constraints.NotBlank;
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
public class UserDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String email;

    @NotNull
    private String fullName;
}
