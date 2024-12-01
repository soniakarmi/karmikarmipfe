package com.example.Backend.Dto.Requests;

import com.example.Backend.Entities.Etudiant;
import com.example.Backend.Entities.QuizProposition;
import com.example.Backend.Entities.QuizQuestion;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class QuizResponseRequest {
    private Long question;
    private Long propositionResponse;
}
