package BodyBuddy.demo.domain.mainPage;

import BodyBuddy.demo.domain.inBody.repository.InBodyRepository;
import BodyBuddy.demo.domain.mainPage.DTO.InBodyDTO;
import BodyBuddy.demo.domain.mainPage.DTO.MainPageDTO;
import BodyBuddy.demo.global.common.member.domain.Member;
import BodyBuddy.demo.global.common.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MainPageService {

    private final InBodyRepository inBodyRepository;
    private final MemberRepository memberRepository;

    public MainPageService(InBodyRepository inBodyRepository, MemberRepository memberRepository) {
        this.inBodyRepository = inBodyRepository;
        this.memberRepository = memberRepository;
    }

    public MainPageDTO getMainPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        //Exception 처리를 Illegal, Runtime, NullPointer Exception 중에서 헷갈림

        InBodyDTO recentInBody = inBodyRepository.findFirstByMemberIdOrderByCreatedAtDesc(memberId)
                .map(inBody -> new InBodyDTO(inBody.getWeight(), inBody.getMuscle(), inBody.getBodyFat()))
                .orElse(null); // 최근 InBody가 없을 경우 null 처리

        return MainPageDTO.builder()
                .recentInBody(recentInBody)
                .build();
    }

}
