package BodyBuddy.demo.domain.calendar;

import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;

public class CalendarMapper {

    public static CalendarResponse toResponse(Calendar calendar) {
        return CalendarResponse.builder()
            .id(calendar.getId())
            .date(calendar.getDate())
            .evaluationStatus(calendar.getEvaluationStatus().name())
            .build();
    }
}
