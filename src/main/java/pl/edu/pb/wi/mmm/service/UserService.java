package pl.edu.pb.wi.mmm.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.UserDto;
import pl.edu.pb.wi.mmm.dto.mapper.UserMapper;
import pl.edu.pb.wi.mmm.entity.User;
import pl.edu.pb.wi.mmm.repository.RoleRepository;
import pl.edu.pb.wi.mmm.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email: [%s] not found".formatted(email)));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: [%s] not found".formatted(userId)));
    }

    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::map);
    }

}


