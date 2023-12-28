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
public class AnswerFilter implements Serializable {

    private String likeContent;

    private UUID id;

    private List<UUID> inIds;

    private UUID userId;

    private boolean correct;
}
