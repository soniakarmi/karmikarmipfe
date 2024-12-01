package com.example.Backend.Dto.Responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizResult {
    private float note;
    private String phrase;
}
