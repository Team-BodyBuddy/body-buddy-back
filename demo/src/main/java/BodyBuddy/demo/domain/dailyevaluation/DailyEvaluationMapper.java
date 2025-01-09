package BodyBuddy.demo.domain.dailyevaluation;

import BodyBuddy.demo.domain.calendar.Calendar;
import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.dailyevaluation.dto.DailyEvaluationResponse;

public class DailyEvaluationMapper {

    public static DailyEvaluationResponse toResponse(DailyEvaluation dailyEvaluation) {
        return DailyEvaluationResponse.builder()
            .id(dailyEvaluation.getId())
            .date(dailyEvaluation.getDate())
            .evaluationStatus(dailyEvaluation.getEvaluation())
            .build();
    }
}
