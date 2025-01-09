package BodyBuddy.demo.domain.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	/**
	 * 회원 생성
	 */
	public Member createMember(MemberRequest request) {
		if (memberRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}
		Member member = new Member();
		member.setName(request.getName());
		member.setEmail(request.getEmail());
		member.setPassword(request.getPassword());
		return memberRepository.save(member);

	}

	/**
	 *모든 회원 조회
	 */
	public List<Member>getAllMembers(){
		return memberRepository.findAll();
	}

	/**
	 *특정 회원 조회
	 */
	public Member getMemberById(Long id){
		return memberRepository.findById(id)
			.orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원 ID입니다: "+id));
	}

	/**
	 * 회원 삭제
	 */
	@Transactional
	public void deleteMember(Long id){
		if(!memberRepository.existsById(id)){
			throw new IllegalArgumentException("존재하지 않는 회원 ID입니다: "+id);
		}
		memberRepository.deleteById(id);
	}






}
