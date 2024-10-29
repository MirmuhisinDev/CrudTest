package org.example.crudtest.controller;

import lombok.RequiredArgsConstructor;
import org.example.crudtest.payload.ApiResponse;
import org.example.crudtest.payload.request.RequestLogin;
import org.example.crudtest.payload.request.RequestUser;
import org.example.crudtest.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AutController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RequestUser user){
        ApiResponse register = authService.register(user);
        return ResponseEntity.status(register.getStatus()).body(register);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody RequestLogin user){
        ApiResponse login = authService.login(user);
        return ResponseEntity.ok(login);

    }
    @PutMapping("/code")
    public ResponseEntity<ApiResponse> activeCode(@RequestParam int code){
        ApiResponse activate = authService.activate(code);
        return ResponseEntity.status(activate.getStatus()).body(activate);
    }
}
