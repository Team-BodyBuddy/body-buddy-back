package BodyBuddy.demo.domain.member.converter;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.member.dto.SignUpRequestDto;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member toMemberEntity(SignUpRequestDto.MemberSignupRequest request, Gym gym) {
        return Member.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .realName(request.getRealName())
                .gender(request.getGender())
                .birthday(request.getBirthday())
                .height(request.getHeight())
                .weight(request.getWeight())
                .region(request.getRegion())
                .gym(gym)
                .build();
    }

    public Trainer toTrainerEntity(SignUpRequestDto.TrainerSignupRequest request, Gym gym) {
        return Trainer.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .realName(request.getRealName())
                .gender(request.getGender())
                .birthday(request.getBirthday())
                .height(request.getHeight())
                .weight(request.getWeight())
                .region(request.getRegion())
                .gym(gym)
                .build();
    }
}