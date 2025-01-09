package BodyBuddy.demo.domain.memberInbody.service;

import BodyBuddy.demo.domain.member.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.memberInbody.MemberInbody;
import BodyBuddy.demo.domain.memberInbody.dto.InbodyResponseDto;
import BodyBuddy.demo.domain.memberInbody.repository.MemberInbodyRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InbodyService {

    private final MemberInbodyRepository memberInbodyRepository;
    private final MemberRepository memberRepository;

    public InbodyService(MemberInbodyRepository memberInbodyRepository, MemberRepository memberRepository) {
        this.memberInbodyRepository = memberInbodyRepository;
        this.memberRepository = memberRepository;
    }

    // PDF 업로드 처리
    public InbodyResponseDto uploadPdf(Long memberId, MultipartFile pdfFile) {
        // PDF 유효성 검사 (로직은 향후 추가할 예정입니다.)
        if (!isValidPdf(pdfFile)) {
            return new InbodyResponseDto("0000년 00월 00일", false, "잘못된 형식이에요!");
        }

        // Member 확인
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 업로드 정보 저장
        MemberInbody memberInbody = new MemberInbody();
        memberInbody.setMember(member);
        memberInbody.setUploadDate(LocalDateTime.now());
        memberInbody.setPdfFileName(pdfFile.getOriginalFilename());
        memberInbodyRepository.save(memberInbody);

        // 응답 생성
        return new InbodyResponseDto(
            memberInbody.getUploadDate().toLocalDate().toString(),
            true,
            "PDF 등록 성공"
        );
    }

    // 마지막 등록 날짜 조회
    public String getLastUploadDate(Long memberId) {
        return memberInbodyRepository.findFirstByMemberIdOrderByUploadDateDesc(memberId)
            .map(memberInbody -> {
                if (memberInbody.getUploadDate() != null) {
                    return memberInbody.getUploadDate().toLocalDate().toString();}
                return "0000년 00월 00일"; // 업로드 날짜가 없는 경우 기본값 반환.
            })
            .orElse("0000년 00월 00일"); // 데이터가 없는 경우 기본값 반환
    }



    // PDF 유효성 검사 (임시 구현)
    private boolean isValidPdf(MultipartFile file) {
        return true;
    }
}
