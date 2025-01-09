package BodyBuddy.demo.domain.member;

import BodyBuddy.demo.domain.member.dto.MemberResponseDto;
import BodyBuddy.demo.domain.member.dto.MemberUpdateRequestDto;

public class MemberMapper {

    // Entity -> ResponseDto 변환
    public static MemberResponseDto toResponseDto(Member member) {
        return new MemberResponseDto(
            member.getNickname(),
            member.getLevel(),
            member.getName(),
            member.getGender(),
            member.getBirthDate().toString(),
            member.getRegion(),
            member.getGym(),
            member.getHeight(),
            member.getWeight()
        );
    }

    // UpdateRequestDto -> Entity 변환 (Entity 수정)
    public static void updateMemberFromDto(MemberUpdateRequestDto dto, Member member) {
        member.setRegion(dto.getRegion());
        member.setGym(dto.getGym());
        member.setHeight(dto.getHeight());
        member.setWeight(dto.getWeight());
    }
}
