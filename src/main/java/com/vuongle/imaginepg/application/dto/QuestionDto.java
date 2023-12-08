package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto implements Serializable {


    private UUID id;

    private String content;

    private List<AnswerDto> answers;

    private User user;

}
