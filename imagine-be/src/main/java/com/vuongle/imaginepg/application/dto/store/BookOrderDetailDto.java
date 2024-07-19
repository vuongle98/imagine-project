package com.vuongle.imaginepg.application.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderDetailDto implements Serializable {

    private UUID id;
    private BookDto book;
    private int amount;
}
