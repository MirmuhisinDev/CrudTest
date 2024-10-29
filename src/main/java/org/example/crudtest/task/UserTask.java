package org.example.crudtest.task;


import lombok.RequiredArgsConstructor;
import org.example.crudtest.entity.User;
import org.example.crudtest.repository.UserRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class UserTask {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteAllEnabledFalseUsers() {
        List<User> allByEnabledFalseAndActivationCodeIsNotNull =
                userRepository.findAllByEnabledFalseAndActivationCodeIsNotNull();
        userRepository.deleteAll(allByEnabledFalseAndActivationCodeIsNotNull);
        System.out.println("scheduled task");
    }
}
