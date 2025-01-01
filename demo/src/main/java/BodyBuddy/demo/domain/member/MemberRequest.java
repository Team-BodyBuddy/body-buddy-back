package BodyBuddy.demo.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
	private String name;
	private String email;
	private String password;
}
