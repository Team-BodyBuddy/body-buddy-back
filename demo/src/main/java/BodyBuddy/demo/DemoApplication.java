package BodyBuddy.demo;

import BodyBuddy.demo.domain.calendar.Calendar;
import BodyBuddy.demo.domain.calendar.dto.CalendarRequest;
import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import BodyBuddy.demo.domain.dailyevaluation.dto.EvaluationRequest;
import BodyBuddy.demo.domain.dailyevaluation.service.DailyEvaluationService;
import BodyBuddy.demo.domain.member.Member;
import BodyBuddy.demo.domain.member.MemberRequest;
import BodyBuddy.demo.domain.member.MemberService;
import BodyBuddy.demo.domain.routine.Routine;
import BodyBuddy.demo.domain.routine.dto.RoutineRequest;
import BodyBuddy.demo.domain.routine.RoutineType;
import BodyBuddy.demo.domain.routine.dto.RoutineResponse;
import BodyBuddy.demo.domain.routine.service.RoutineService;
import BodyBuddy.demo.domain.EvaluationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

//테스트 코드 짜기전에 임시로 해봄
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private DailyEvaluationService dailyEvaluationService;

    @Autowired
    private RoutineService routineService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // 1. 회원 생성
        MemberRequest request = new MemberRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");

        Member savedMember = memberService.createMember(request);
        System.out.println("회원 생성: " + savedMember);

        // 2. 캘린더 데이터 생성
        CalendarRequest calendarRequest = new CalendarRequest();
        calendarRequest.setMemberId(savedMember.getId());
        calendarRequest.setDate(LocalDate.now());
        calendarRequest.setEvaluationStatus(EvaluationStatus.GOOD);
        CalendarResponse calendar = calendarService.createOrUpdateCalendar(calendarRequest);
        System.out.println("캘린더 생성: " + calendar);

        // 3. 하루 평가 데이터 생성
        EvaluationRequest evaluationRequest = new EvaluationRequest();
        evaluationRequest.setMemberId(savedMember.getId());
        evaluationRequest.setDate(LocalDate.now());
        evaluationRequest.setEvaluationStatus(EvaluationStatus.SOSO);
        dailyEvaluationService.saveDailyEvaluation(evaluationRequest);
        System.out.println("하루 평가 데이터 생성 완료");

        // 4. 루틴 추가
        RoutineRequest routineRequest = new RoutineRequest();
        routineRequest.setMemberId(savedMember.getId());
        routineRequest.setName("Morning Workout");
        routineRequest.setType(RoutineType.ROUTINE);
        routineRequest.setDate(LocalDate.now());
        RoutineResponse routine = routineService.addRoutine(routineRequest);
        System.out.println("루틴 추가: " + routine);

        // 5. 루틴 삭제
        routineService.deleteRoutine(routine.getId());
        System.out.println("루틴 삭제 완료");

        // 6. 회원 삭제
        memberService.deleteMember(savedMember.getId());
        System.out.println("회원 삭제 완료");
    }
}
