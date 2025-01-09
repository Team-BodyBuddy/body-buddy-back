package BodyBuddy.demo.domain.routine;

import BodyBuddy.demo.domain.dailyevaluation.DailyEvaluation;
import BodyBuddy.demo.domain.dailyevaluation.dto.DailyEvaluationResponse;
import BodyBuddy.demo.domain.routine.dto.RoutineResponse;
import java.time.LocalDate;

public class RoutineMapper {

    public static RoutineResponse toResponse(Routine routine) {
        return RoutineResponse.builder()
            .id(routine.getId())
            .name(routine.getName())
            .type(routine.getType())
            .completed(routine.getCompleted())
            .date(routine.getDate())
            .build();
    }
}
