package org.example.crudtest.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.example.crudtest.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserRepositoryTest {
    @Autowired
    @Mock
    private UserRepository userRepository;
    @Test
    public void UserRepository_SaveAll_ReturnSavedUser(){


        //Arrange  Tartibga solish

        User user = User.builder()
                .fullName("Super_Admin")
                .email("super_admin2")
                .password("admin123")
                .build();

        //Act  Qanun

        User savedUser = userRepository.save(user);

        //Assert  Tasdiqlash

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);


    }
}
