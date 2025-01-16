package BodyBuddy.demo.domain.Trainer.service;

import BodyBuddy.demo.domain.Trainer.entity.Trainer;
import BodyBuddy.demo.domain.Trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.common.member.repository.MemberRepository;
import BodyBuddy.demo.global.common.member.DTO.MemberDTO;
import BodyBuddy.demo.global.common.member.DTO.MemberDTO.MemberInquiry;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {

  private final TrainerRepository trainerRepository;
  private final MemberRepository memberRepository;

  public Page<MemberInquiry> getTrainerMembers(Long trainerId, int page, int size) {
    Trainer trainer = trainerRepository.findById(trainerId)
        .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

    Pageable pageable = PageRequest.of(page, size);

    return memberRepository.findByTrainerId(trainerId, pageable)
        .map(member -> new MemberDTO.MemberInquiry(
            member.getId(),
            member.getRealName(),
            member.getProfileImage(),
            member.getLevel()
        ));

  }
}
