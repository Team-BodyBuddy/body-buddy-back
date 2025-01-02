package BodyBuddy.demo.domain.dailyevaluation.dto;

import BodyBuddy.demo.domain.EvaluationStatus;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyEvaluationResponse {

    private Long id;
    private LocalDate date;
    private EvaluationStatus evaluationStatus; // BAD, SOSO, GOOD
}