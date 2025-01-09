package BodyBuddy.demo.domain.member.service;

import BodyBuddy.demo.domain.member.Member;
import BodyBuddy.demo.domain.member.MemberMapper;
import BodyBuddy.demo.domain.member.dto.MemberResponseDto;
import BodyBuddy.demo.domain.member.dto.MemberUpdateRequestDto;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 조회
    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return MemberMapper.toResponseDto(member);
    }

    // 회원 수정
    public void updateMember(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        MemberMapper.updateMemberFromDto(requestDto, member);
        memberRepository.save(member);
    }
}
