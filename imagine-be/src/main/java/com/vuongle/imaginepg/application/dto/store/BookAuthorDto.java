package com.vuongle.imaginepg.application.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAuthorDto implements Serializable {

    private UUID id;

    private String fullName;

    private String email;

    private String phone;

    private String address;
}
