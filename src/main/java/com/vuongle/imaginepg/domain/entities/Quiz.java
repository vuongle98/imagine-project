package com.vuongle.imaginepg.domain.entities;

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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "quiz_questions",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    private File image;

    private Instant deletedAt;

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

    public void addQuestion(Question question) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }

        this.questions.add(question);
    }

    public void addQuestions(List<Question> questions) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }

        this.questions.addAll(questions);
    }

    public void removeQuestion(UUID id) {
        if (this.questions == null) return;

        this.questions.removeIf(q -> q.getId().equals(id));
    }

    public void removeQuestions(List<UUID> ids) {
        if (this.questions == null) return;

        for (var id: ids) {
            removeQuestion(id);
        }
    }

}
