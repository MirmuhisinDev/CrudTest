package org.example.crudtest.payload;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Pageable {

    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private Object body;
}
