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
public class BookFilter implements Serializable {

    private String likeTitle;

    private String likeDescription;

    private Instant publishedFrom;

    private long priceFrom;

    private int edition;

    private UUID authorId;

}
