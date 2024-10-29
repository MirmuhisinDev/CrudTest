package org.example.crudtest.service;

import lombok.RequiredArgsConstructor;
import org.example.crudtest.entity.User;
import org.example.crudtest.entity.enums.Role;
import org.example.crudtest.payload.ApiResponse;
import org.example.crudtest.payload.Pageable;
import org.example.crudtest.payload.reponse.UserResponse;
import org.example.crudtest.payload.request.RequestUser;
import org.example.crudtest.payload.request.UpdateUser;
import org.example.crudtest.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ApiResponse getById(int id){
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()){
            UserResponse userResponse = UserResponse.builder()
                    .id(byId.get().getId())
                    .fullName(byId.get().getFullName())
                    .email(byId.get().getEmail())
                    .build();
            return new ApiResponse(userResponse);
        }
        return new ApiResponse("usr not found", 404);
    }
    public ApiResponse getAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> all = userRepository.findAll(pageRequest);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : all) {
            UserResponse response = UserResponse.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .build();
            userResponses.add(response);
        }
        Pageable pageable = Pageable.builder()
                .size(all.getSize())
                .page(all.getNumber())
                .totalPages(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .body(userResponses)
                .build();
        return new ApiResponse(pageable);
    }

    public ApiResponse update(UpdateUser user, Integer id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            User user1 = byId.get();
            user1.setFullName(user.getFullName());
            user1.setEmail(user.getEmail());
            userRepository.save(user1);
            return new ApiResponse("user update", 201);
        }
        return new ApiResponse("user not found", 404);
    }

    public ApiResponse deleteById (int id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            userRepository.delete(byId.get());
            return new ApiResponse("user deleted", 200);
        }
        return new ApiResponse("user not found", 404);
    }

    public ApiResponse searchByFullName(String fullName){
        List<User> users = userRepository.searchByFullName(fullName);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse response = UserResponse.builder()
                    .fullName(user.getFullName())
                    .email(user.getUsername())
                    .id(user.getId())
                    .build();
            userResponses.add(response);
        }
        return new ApiResponse(userResponses);
    }
    public ApiResponse saveAdmin(RequestUser requestUser){
        Optional<User> byUsername = userRepository.findByEmail(requestUser.getEmail());
        if(byUsername.isPresent()){
            return new ApiResponse("already exists", 400);
        }
        User user = User.builder()
                .fullName(requestUser.getFullName())
                .email(requestUser.getEmail())
                .password(passwordEncoder.encode(requestUser.getPassword()))
                .role(Role.ROLE_ADMIN)
                .build();
        userRepository.save(user);
        return new ApiResponse("admin saved successfully", 200);
    }
}