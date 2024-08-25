package com.tenten.damoa.verificationcode.dto;

import com.tenten.damoa.member.domain.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerificationReq {

    private final String account;

    private final String email;

    private final String password;

    private final String code;

}
