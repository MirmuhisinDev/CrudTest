package org.example.crudtest.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ApiResponse {
    private String message;
    private Integer status;
    private Object body;

    public ApiResponse(Object body) {
        this.body = body;
    }

    public ApiResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
