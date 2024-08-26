package com.tenten.damoa.verification.domain;

import com.tenten.damoa.common.util.RandomCodeUtil;
import com.tenten.damoa.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime sendAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public VerificationCode(String code, LocalDateTime sendAt, Member member) {
        this.code = code;
        this.sendAt = sendAt;
        this.member = member;
    }

    public static VerificationCode create(Member member) {
        return VerificationCode.builder()
            .code(RandomCodeUtil.getRandomCode())
            .sendAt(LocalDateTime.now())
            .member(member)
            .build();
    }
}
