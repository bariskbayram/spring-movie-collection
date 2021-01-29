package com.bkb.springmoviecollection;

import com.bkb.springmoviecollection.model.entity.User;
import com.bkb.springmoviecollection.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringMovieCollectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMovieCollectionApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {
        return args -> {
            User user = new User();
            user.setUsername("user");
            user.setFullname("default user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setUserRole("USER");

            User admin = new User();
            admin.setUsername("admin");
            admin.setFullname("default admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setUserRole("ADMIN");

            userRepository.save(user);
            userRepository.save(admin);
        };
    }

}
