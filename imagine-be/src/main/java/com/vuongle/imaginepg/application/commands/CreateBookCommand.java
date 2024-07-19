package com.vuongle.imaginepg.application.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookCommand implements Serializable {

    private String title;

    private String description;

    private Instant publishedAt;

    private long price;

    private int edition;

    private UUID authorId;



}
