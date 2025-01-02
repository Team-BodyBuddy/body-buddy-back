package BodyBuddy.demo.domain.dailyevaluation.controller;

import BodyBuddy.demo.domain.dailyevaluation.dto.DailyEvaluationResponse;
import BodyBuddy.demo.global.apiPayLoad.ApiResponse;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import BodyBuddy.demo.domain.dailyevaluation.DailyEvaluation;
import BodyBuddy.demo.domain.dailyevaluation.dto.EvaluationRequest;
import BodyBuddy.demo.domain.dailyevaluation.repository.DailyEvaluationRepository;
import BodyBuddy.demo.domain.dailyevaluation.service.DailyEvaluationService;
import jakarta.validation.Valid;
import lombok.Data;

@RestController
@RequestMapping("/api/evaluations")
public class DailyEvaluationController {

    private final DailyEvaluationService dailyEvaluationService;

    public DailyEvaluationController(DailyEvaluationService dailyEvaluationService) {
        this.dailyEvaluationService = dailyEvaluationService;
    }

    /**
     * 오늘 평가 저장 API
     */
    @PostMapping
    public ResponseEntity<ApiResponse<DailyEvaluationResponse>> saveDailyEvaluation(
        @RequestBody @Valid EvaluationRequest request) {
        DailyEvaluationResponse dailyEvaluation = dailyEvaluationService.saveDailyEvaluation(
            request);
        return ResponseEntity.ok(ApiResponse.onSuccess(dailyEvaluation));
    }


}
