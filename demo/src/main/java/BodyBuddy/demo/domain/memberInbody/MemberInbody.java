package BodyBuddy.demo.domain.memberInbody;

import BodyBuddy.demo.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter //바꿀 예정
public class MemberInbody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false) // FK 컬럼명 설정
    @JsonIgnore
    private Member member;

    private LocalDateTime uploadDate; // 업로드 날짜
    private String pdfFileName; // 업로드된 PDF 파일 이름


}
