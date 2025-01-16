package BodyBuddy.demo.domain.Trainer.controller;

import BodyBuddy.demo.domain.Trainer.service.TrainerService;
import BodyBuddy.demo.global.common.member.DTO.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

  private final TrainerService trainerService;

  /**
   * 트레이너에서 회원 조회
   * 0. 회원 이미지
   * 1. 회원 닉네임
   * 2. 트레이너 마이페이지 -> 메일 오면 카운트 +1
   */

  @Operation(summary = "메인페이지에서 회원 조회", description = "메인 페이지에서 회원 조회")
  @GetMapping("/{trainerId}/members")
  public ResponseEntity<List<MemberDTO.MemberInquiry>> getTrainerMembers(@PathVariable Long trainerId) {
    List<MemberDTO.MemberInquiry> members = trainerService.getTrainerMembers(trainerId);
    return ResponseEntity.ok(members);
  }


}
