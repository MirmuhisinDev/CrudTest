package org.example.crudtest.payload.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUser {

    @NotBlank
    @Size(min = 3, max = 50, message = "Uzunligi kamida 3 ta bolishi shart")
    private String fullName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
