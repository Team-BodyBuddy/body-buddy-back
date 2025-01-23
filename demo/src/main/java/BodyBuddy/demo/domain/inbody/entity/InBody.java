package BodyBuddy.demo.domain.inbody.entity;

import java.time.LocalDateTime;

import BodyBuddy.demo.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InBody {

	@Id
	@GeneratedValue
	@Column(name = "inBody_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)   //FK 컬럼 이름
	private Member member;

	private Float weight;

	private Float muscle;

	private Float bodyFat;

	@Column(nullable = false, updatable = false)
	// updatable = false -> update 쿼리 실행 시 createdAt필드 변경 X
	private LocalDateTime createdAt = LocalDateTime.now();

	private String pdfFileName; // 업로드된 PDF 파일 이름

}