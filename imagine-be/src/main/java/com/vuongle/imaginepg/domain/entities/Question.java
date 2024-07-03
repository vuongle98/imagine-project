package com.vuongle.imaginepg.domain.entities;

import com.vuongle.imaginepg.application.commands.SubmitAnswer;
import com.vuongle.imaginepg.domain.constants.QuestionCategory;
import com.vuongle.imaginepg.domain.constants.QuestionLevel;
import com.vuongle.imaginepg.domain.constants.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "question")
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    private int countdown;

    private QuestionLevel level;

    private boolean mark;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;

    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "question_answers",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private List<Answer> answers;

    @ManyToMany(mappedBy = "questions", fetch = FetchType.LAZY)
    private List<Quiz> quizzes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public int checkAnswer(SubmitAnswer answer) {
        int correct = 0;
        List<Answer> correctAnswers = answers.stream().filter(Answer::isCorrect).toList();

        for (Answer ans : correctAnswers) {
            if (answer.getAnswerIds().stream().anyMatch(ansId -> ansId.equals(ans.getId()))) {
                correct++;
            }
        }

        return correct;
    }
}
