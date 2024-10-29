package org.example.crudtest.service;

import lombok.RequiredArgsConstructor;
import org.example.crudtest.entity.User;
import org.example.crudtest.entity.enums.Role;
import org.example.crudtest.payload.ApiResponse;
import org.example.crudtest.payload.request.RequestLogin;
import org.example.crudtest.payload.request.RequestUser;
import org.example.crudtest.payload.request.UpdateUser;
import org.example.crudtest.repository.UserRepository;
import org.example.crudtest.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final EmailSenderService emailSenderService;

    public ApiResponse register(RequestUser user) {

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            return new ApiResponse("User already exists", 400);
        }
        int randomNumber = randomNumber();
        User user1 = User.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .enabled(false)
                .activationCode(randomNumber)
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user1);
        emailSenderService.sendEmail(user.getEmail(),"VERIFY EMAIL", "Your activation code: "+randomNumber);
        return new ApiResponse("user registered successfully", 201);
    }



    public ApiResponse login(RequestLogin user){
        Optional<User> byUsername = userRepository.findByEmail(user.getEmail());
        if (byUsername.isEmpty()) {
            return new ApiResponse("user not found", 404);
        }
        User user1 = byUsername.get();
        if(!user1.isEnabled()) return new ApiResponse("user not enabled", 400);
        boolean matches = passwordEncoder.matches(user.getPassword(), user1.getPassword());
        if (matches) {
            String s = jwtProvider.generateToken(user.getEmail());
            return new ApiResponse(s+":     : "+user1.getRole());
        }
        return new ApiResponse("password don't match", 400);
    }


    public ApiResponse activate(int code){
        Optional<User> byActivationCode = userRepository.findByActivationCode(code);
        if (byActivationCode.isPresent()) {
            User user = byActivationCode.get();
            user.setEnabled(true);
            user.setActivationCode(null);
            userRepository.save(user);
            return new ApiResponse("user activated successfully", 200);
        }
        return new ApiResponse("activation code Error", 400);
    }

    public int randomNumber(){
        Random random = new Random();
        return random.nextInt(90000) + 10000;
    }
}
