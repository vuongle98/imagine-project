package com.vuongle.imaginepg.application.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDto implements Serializable {

    private PostStatistic postStatistic = new PostStatistic();
    private FileStatistic fileStatistic = new FileStatistic();
    private UserStatistic userStatistic = new UserStatistic();
    private QuizStatistic quizStatistic = new QuizStatistic();
    private QuestionStatistic questionStatistic = new QuestionStatistic();
}
