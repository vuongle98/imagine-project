package com.vuongle.imaginepg.application.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuizCommand implements Serializable {

    private String title;
    private List<UUID> addQuestionIds;
    private List<UUID> removeQuestionIds;
}
