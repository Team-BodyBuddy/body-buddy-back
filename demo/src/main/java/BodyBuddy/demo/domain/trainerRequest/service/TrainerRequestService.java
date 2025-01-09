package BodyBuddy.demo.domain.trainerRequest.service;

import BodyBuddy.demo.domain.member.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.domain.trainerRequest.RequestStatus;
import BodyBuddy.demo.domain.trainerRequest.TrainerRequest;
import BodyBuddy.demo.domain.trainerRequest.dto.TrainerRequestDto;
import BodyBuddy.demo.domain.trainerRequest.dto.TrainerRequestResponseDto;
import BodyBuddy.demo.domain.trainerRequest.repository.TrainerRequestRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TrainerRequestService {

    private final TrainerRequestRepository trainerRequestRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    public TrainerRequestService(TrainerRequestRepository trainerRequestRepository, MemberRepository memberRepository, TrainerRepository trainerRepository) {
        this.trainerRequestRepository = trainerRequestRepository;
        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
    }

    // 요청 생성
    public TrainerRequestResponseDto createRequest(TrainerRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Trainer trainer = trainerRepository.findById(requestDto.getTrainerId())
            .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        TrainerRequest request = new TrainerRequest();
        request.setMember(member);
        request.setTrainer(trainer);
        request.setStatus(RequestStatus.PENDING);
        request.setRequestDate(LocalDateTime.now());

        TrainerRequest savedRequest = trainerRequestRepository.save(request);

        return new TrainerRequestResponseDto(
            savedRequest.getId(),
            member.getNickname(),
            trainer.getName(),
            savedRequest.getStatus(),
            savedRequest.getRequestDate()
        );
    }

    // 트레이너가 받은 요청 조회
    public List<TrainerRequestResponseDto> getRequestsForTrainer(Long trainerId) {
        List<TrainerRequest> requests = trainerRequestRepository.findAllByTrainer_Id(trainerId);
        return requests.stream()
            .map(request -> new TrainerRequestResponseDto(
                request.getId(),
                request.getMember().getNickname(),
                request.getTrainer().getName(),
                request.getStatus(),
                request.getRequestDate()
            ))
            .collect(Collectors.toList());
    }

    // 요청 상태 변경 및 처리
    public void updateRequestStatus(Long requestId, RequestStatus status) {
        TrainerRequest request = trainerRequestRepository.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (status == RequestStatus.ACCEPTED) {
            // 요청 수락: 회원을 트레이너의 관리 대상에 추가
            Member member = request.getMember();
            Trainer trainer = request.getTrainer();

            trainer.addMember(member); // 트레이너에 멤버 추가
            trainerRepository.save(trainer); // 변경된 트레이너 저장
        }

        // 요청 삭제
        trainerRequestRepository.delete(request);
    }
}
