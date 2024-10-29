package org.example.crudtest.payload.reponse;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private int id;
    private String fullName;
    private String email;
}
