package BodyBuddy.demo.domain.notification.entity;

import java.time.LocalDateTime;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long id; // 알림 ID

	@Column(nullable = false)
	private String title; // 알림 제목

	@Column(nullable = false)
	private String message; // 알림 내용

	@Column(nullable = false)
	private boolean isRead; // 읽음 여부 (true: 읽음, false: 안 읽음)

	@Column(nullable = false)
	private LocalDateTime createdAt; // 알림 생성 시간

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatar_id", nullable = false)
	private Avatar avatar; // 알림 수신자

}