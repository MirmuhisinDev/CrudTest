package org.example.crudtest.dataloader;

import lombok.RequiredArgsConstructor;
import org.example.crudtest.entity.User;
import org.example.crudtest.entity.enums.Role;
import org.example.crudtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;
    @Override
    public void run(String... args) throws Exception {
        if(ddl.equals("create")){
            User user = User.builder()
                    .fullName("Super_Admin")
                    .email("super_admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_SUPER_ADMIN)
                    .build();
            userRepository.save(user);
        }
    }
}
