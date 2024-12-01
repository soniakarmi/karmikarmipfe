package com.example.Backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizProposition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @Temporal(TemporalType.DATE)
    private LocalDate created_at;

    @ManyToOne
    @JoinColumn(name = "quizQuestion", referencedColumnName = "id")
    private QuizQuestion quizQuestion;

    @OneToMany(mappedBy ="quizProposition", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<QuizResponse> quizResponses;
}