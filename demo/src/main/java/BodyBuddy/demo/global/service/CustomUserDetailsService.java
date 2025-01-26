package BodyBuddy.demo.global.service;

import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Member 조회
        var member = memberRepository.findByLoginId(username).orElse(null);

        if (member != null) {
            return User.builder()
                    .username(member.getLoginId())
                    .password(member.getPassword())
                    .authorities("ROLE_MEMBER")
                    .build();
        }

        // Trainer 조회
        var trainer = trainerRepository.findByLoginId(username).orElseThrow(() ->
                new UsernameNotFoundException("유저를 찾을 수 없습니다.: " + username));

        return User.builder()
                .username(trainer.getLoginId())
                .password(trainer.getPassword())
                .authorities("ROLE_TRAINER")
                .build();
    }
}
