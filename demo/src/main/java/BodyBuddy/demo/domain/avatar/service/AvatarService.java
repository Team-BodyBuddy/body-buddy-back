package BodyBuddy.demo.domain.avatar.service;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public Avatar createDefaultAvatar(Member member) {
        Avatar avatar = Avatar.builder()
            .level(1L)
            .exp(0L)
            .point(0L)
            .rankingScore(0L)
            .member(member)
            .build();
        return avatarRepository.save(avatar);
    }
}
