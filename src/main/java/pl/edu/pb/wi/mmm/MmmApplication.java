package pl.edu.pb.wi.mmm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.pb.wi.mmm.dto.RegisterRequest;
import pl.edu.pb.wi.mmm.repository.UserRepository;
import pl.edu.pb.wi.mmm.service.AuthenticationService;

@SpringBootApplication
public class MmmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmmApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            AuthenticationService authenticationService
    ) {
        return args -> {
            if (userRepository.findByEmail("admin@mail.com").isEmpty()) {
                var admin = RegisterRequest.builder()
                        .firstName("Admin")
                        .lastName("Admin")
                        .email("admin@mail.com")
                        .password("password")
                        .build();

                System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken());
            }
        };
    }

}
