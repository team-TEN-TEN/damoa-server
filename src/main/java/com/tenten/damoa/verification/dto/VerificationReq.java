package com.tenten.damoa.verification.dto;

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
