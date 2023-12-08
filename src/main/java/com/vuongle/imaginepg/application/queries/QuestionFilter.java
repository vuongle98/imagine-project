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
public class QuestionFilter implements Serializable {

    private UUID id;

    private List<UUID> inIds;

    private String likeContent;

    private UUID creatorId;

    private String likeAnswer;
}
