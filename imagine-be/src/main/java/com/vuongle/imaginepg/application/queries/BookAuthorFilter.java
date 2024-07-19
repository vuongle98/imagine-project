package com.vuongle.imaginepg.application.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAuthorFilter implements Serializable {

    private String likeName;

    private String email;

    private String likePhone;

    private String likeAddress;

}
