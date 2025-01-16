package BodyBuddy.demo.domain.Trainer.service;

import BodyBuddy.demo.domain.Trainer.entity.Trainer;
import BodyBuddy.demo.domain.Trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.common.member.DTO.MemberDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {

  private final TrainerRepository trainerRepository;

  public List<MemberDTO.MemberInquiry> getTrainerMembers(Long trainerId) {
    Trainer trainer = trainerRepository.findById(trainerId)
        .orElseThrow(() -> new EntityNotFoundException("트레이너가 없습니다"));

    return trainer.getMembers().stream()
        .map(member -> MemberDTO.MemberInquiry.builder()
            .id(member.getId())
            .realName(member.getRealName())
            .level(member.getLevel())
            .profileImage(member.getProfileImage())
            .build())
        .collect(Collectors.toList());
  }

}
