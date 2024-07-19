package com.vuongle.imaginepg.application.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookOrderDetailCommand implements Serializable {

    private UUID orderId;
    private UUID bookId;
    private int amount;

}
