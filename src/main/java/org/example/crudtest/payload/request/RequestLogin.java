package org.example.crudtest.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestLogin {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
