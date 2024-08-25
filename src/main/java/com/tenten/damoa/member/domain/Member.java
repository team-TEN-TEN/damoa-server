package com.tenten.damoa.member.domain;

import static com.tenten.damoa.common.exception.ErrorCode.PRE_MEMBER_FORBIDDEN;

import com.tenten.damoa.common.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    private LocalDateTime joinedAt;

    public void verifyMemberRole() {
        if (role == MemberRole.PRE_MEMBER) {
            throw new BusinessException(PRE_MEMBER_FORBIDDEN);
        }
    }
}
