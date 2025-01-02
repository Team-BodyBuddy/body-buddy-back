package BodyBuddy.demo.domain.routine.dto;

import BodyBuddy.demo.domain.routine.RoutineType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResponse {

    private Long id;
    private String name;
    private RoutineType type; // CLASS, ROUTINE
    private boolean completed;
    private LocalDate date;
}