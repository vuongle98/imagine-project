package com.vuongle.imaginepg.application.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderDto implements Serializable {

    private UUID bookId;
    private int amount;
}
