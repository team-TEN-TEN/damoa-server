package com.tenten.damoa.verificationcode.dto;

import lombok.Data;

@Data
public class VerificationRes {

    private final boolean success;
    private final String message;

}
