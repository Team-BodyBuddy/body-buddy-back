package BodyBuddy.demo.domain.calendar.dto;

import BodyBuddy.demo.domain.calendar.Calendar;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarResponse {

    private Long id;
    private LocalDate date;
    private String evaluationStatus; // BAD, SOSO, GOOD


}
