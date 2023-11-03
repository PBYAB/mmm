package pl.edu.pb.wi.mmm.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pb.wi.mmm.dto.UserDto;
import pl.edu.pb.wi.mmm.dto.UserRegisterDto;
import pl.edu.pb.wi.mmm.dto.mapper.UserMapper;
import pl.edu.pb.wi.mmm.entity.User;
import pl.edu.pb.wi.mmm.exception.EmailAlreadyExists;
import pl.edu.pb.wi.mmm.exception.LoginAlreadyExists;
import pl.edu.pb.wi.mmm.repository.RoleRepository;
import pl.edu.pb.wi.mmm.repository.UserRepository;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

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

    @Transactional
    public void register(UserRegisterDto userRegisterDTO) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new EmailAlreadyExists("User with email: [%s] already exists".formatted(userRegisterDTO.getEmail()));
        }

        if (userRepository.existsByLogin(userRegisterDTO.getLogin())) {
            throw new LoginAlreadyExists("User with login: [%s] already exists".formatted(userRegisterDTO.getLogin()));
        }

        User user = User.builder()
                .login(userRegisterDTO.getLogin())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .name(userRegisterDTO.getName())
                .surname(userRegisterDTO.getSurname())
                .roles(Set.of(roleRepository.findByRole("ROLE_USER")))
                .enabled(Boolean.TRUE)
                .build();

        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void update(Long userId, UserDto updatedData) {
        User user = findById(userId);

        user.setName(updatedData.getName());
        user.setSurname(updatedData.getSurname());
        user.setEmail(updatedData.getEmail());
        user.setRoles(updatedData.getRoles());
    }
}


