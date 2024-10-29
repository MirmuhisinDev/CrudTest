package org.example.crudtest.controller;


import io.swagger.annotations.Api;
import jakarta.servlet.http.PushBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crudtest.entity.User;
import org.example.crudtest.payload.ApiResponse;
import org.example.crudtest.payload.request.RequestUser;
import org.example.crudtest.payload.request.UpdateUser;
import org.example.crudtest.service.AuthService;
import org.example.crudtest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOneUser(@PathVariable int id){
        ApiResponse byId = userService.getById(id);
        return ResponseEntity.ok(byId);
    }
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllUsers(@RequestParam (defaultValue = "5") int size,
                                                   @RequestParam (defaultValue = "0") int page){
        ApiResponse all = userService.getAll(page, size);
        return ResponseEntity.ok(all);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id){
        ApiResponse delete = userService.deleteById(id);
        return ResponseEntity.ok(delete);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable int id, @RequestBody UpdateUser updateUser){
        ApiResponse update = userService.update(updateUser, id);
        return ResponseEntity.ok(update);
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchByFullName(@RequestParam("fullName") String fullName){
        ApiResponse byFullName = userService.searchByFullName(fullName);
        return ResponseEntity.ok(byFullName);
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("save-admin")
    public ResponseEntity<ApiResponse> saveAdmin(@RequestBody @Valid RequestUser user){
        ApiResponse apiResponse = userService.saveAdmin(user);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}