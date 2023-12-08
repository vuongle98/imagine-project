package com.vuongle.imaginepg.application.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizFilter implements Serializable {

    private UUID id;

    private String likeQuestion;

    private UUID questionId;

    private String likeTitle;

}
