package com.vuongle.imaginepg.application.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter implements Serializable {

    private String likeUsername;
    private String likeFullName;
    private String likeEmail;

    private List<UUID> inIds;

    private UUID id;
}
