package BodyBuddy.demo.domain.routine.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.routine.RoutineType;
import lombok.Data;

@Data
public class RoutineRequest {
	private Long memberId;
	private String name;
	private RoutineType type;
	private LocalDate date;

}