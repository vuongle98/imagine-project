package com.vuongle.imaginepg.application.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagFilter implements Serializable {

    private UUID id;
    private String likeName;
}
