package com.vuongle.imaginepg.application.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilter implements Serializable {

    private UUID customerId;
    private UUID discountId;
}
