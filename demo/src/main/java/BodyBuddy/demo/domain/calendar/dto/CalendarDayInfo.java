package BodyBuddy.demo.domain.calendar.dto;

import BodyBuddy.demo.global.common.commonEnum.EvaluationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDayInfo {

	private IndicatorType indicator; // 점 상태 (BOTH, BLUE, RED, NONE)
	private EvaluationStatus evaluationStatus; // 평가 상태 (BAD, MODERATE, GOOD, NONE)

	public enum IndicatorType {
		NONE, BOTH, BLUE, RED
	}

}