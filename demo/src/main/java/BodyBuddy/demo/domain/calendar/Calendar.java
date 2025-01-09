package BodyBuddy.demo.domain.calendar;

import BodyBuddy.demo.domain.calendar.dto.CalendarDayInfo.IndicatorType;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import BodyBuddy.demo.domain.EvaluationStatus;
import BodyBuddy.demo.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore // JSON 직렬화에서 무시
    private Member member;

    private LocalDate date;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private EvaluationStatus evaluationStatus; // BAD,SOSO,GOOD

    @Enumerated(EnumType.STRING)
    private IndicatorType indicator = IndicatorType.NONE; // 점 상태 (NONE이 기본값)


}
