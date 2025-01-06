package BodyBuddy.demo.global.common.service;

import BodyBuddy.demo.global.common.dto.MemberDto;
import BodyBuddy.demo.global.common.entity.ActivityType;
import BodyBuddy.demo.global.common.entity.Member;
import BodyBuddy.demo.global.common.repository.MemberRepository;
import BodyBuddy.demo.gym.entity.Gym;
import BodyBuddy.demo.gym.repository.GymRepository;
import BodyBuddy.demo.ranking.entity.RankingPoint;
import BodyBuddy.demo.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 관련 서비스 계층 (MemberService)
 * 회원 데이터 관리 및 경험치 추가 로직 처리
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberExperienceService memberExperienceService;
    private final GymRepository gymRepository;
    private final RankingRepository rankingRepository;

    /**
     * 모든 회원 목록 조회
     * @return 회원 DTO 리스트
     */
    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 회원 조회
     * @param id 회원 ID
     * @return 회원 DTO
     */
    public MemberDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return toDto(member);
    }

    /**
     * 새로운 회원 생성
     * @param memberDto 회원 데이터
     * @return 생성된 회원 DTO
     */
    public MemberDto createMember(MemberDto memberDto) {
        Member member = new Member();
        member.setNickname(memberDto.getNickname());
        member.setLevel(memberDto.getLevel());
        member.setHeight(memberDto.getHeight());
        member.setWeight(memberDto.getWeight());

        // gymId로 Gym 엔티티 조회 및 설정
        if (memberDto.getGymId() != null) {
            Gym gym = gymRepository.findById(memberDto.getGymId())
                    .orElseThrow(() -> new IllegalArgumentException("Gym not found"));
            member.setGym(gym);
        }

        Member savedMember = memberRepository.save(member);

        // 새로운 회원의 초기 랭킹 데이터 생성
        createInitialRanking(savedMember);

        return toDto(savedMember);
    }

    /**
     * 경험치 추가 및 레벨업 처리
     * @param memberId 회원 ID
     * @param activityType 활동 유형
     * @param streakDays 연속 운동 일수
     * @param rank 랭킹
     */
    public void addExperience(Long memberId, ActivityType activityType, int streakDays, int rank) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 경험치 계산
        int exp = memberExperienceService.calculateExperience(member.getLevel(), activityType, streakDays, rank);
        member.addExperience(exp);

        // 레벨업 확인
        int expForNextLevel = memberExperienceService.calculateExpForNextLevel(member.getLevel());
        if (memberExperienceService.checkLevelUp(member.getExperience(), expForNextLevel)) {
            member.levelUp();
        }

        memberRepository.save(member);
    }

    /**
     * Member 엔티티를 MemberDto로 변환
     * @param member 회원 엔티티
     * @return 회원 DTO
     */
    private MemberDto toDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setNickname(member.getNickname());
        dto.setLevel(member.getLevel());
        dto.setHeight(member.getHeight());
        dto.setWeight(member.getWeight());

        if (member.getGym() != null) {
            dto.setGymId(member.getGym().getId());
        } else {
            dto.setGymId(null);
        }

        return dto;
    }

    private void createInitialRanking(Member member) {
        RankingPoint rankingPoint = RankingPoint.builder()
                .member(member)
                .totalScore(0)
                .activityScore(0)
                .goalBonus(0)
                .intensityBonus(0)
                .build();
        rankingRepository.save(rankingPoint);
    }
}
