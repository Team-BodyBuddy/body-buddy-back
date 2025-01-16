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

  public List<MemberDTO.Response> getTrainerMembers(Long trainerId) {
    Trainer trainer = trainerRepository.findById(trainerId)
        .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

    return trainer.getMembers().stream()
        .map(member -> new MemberDTO(
            member.getId(),
            member.getRealName(),
            member.getLevel(),
            member.getAvatars().isEmpty() ? null : member.getAvatars().get(0).getLevel()
        ))
        .collect(Collectors.toList());
  }

}
