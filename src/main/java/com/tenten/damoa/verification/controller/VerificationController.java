package com.tenten.damoa.verification.controller;

import com.tenten.damoa.verification.dto.VerificationReq;
import com.tenten.damoa.verification.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증")
@RestController
@RequestMapping("/api/v1")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @Operation(summary = "사용자 인증", description = "메일로 발송된 인증 코드로 사용자를 인증하는 API")
    @PostMapping("/members/verify")
    public ResponseEntity<String> verifyMember(@RequestBody VerificationReq req) {
         verificationService.memberVerification(req);
         return ResponseEntity.ok("가입되었습니다");
    }
}
