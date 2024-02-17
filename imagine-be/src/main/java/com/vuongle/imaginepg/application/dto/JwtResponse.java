package com.vuongle.imaginepg.application.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse implements Serializable {

    private String token;

    @Builder.Default
    private String type = "Bearer";

    private UserDto user;
}
