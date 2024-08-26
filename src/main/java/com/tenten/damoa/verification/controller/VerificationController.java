package com.tenten.damoa.verification.controller;

import com.tenten.damoa.verification.dto.VerificationReq;
import com.tenten.damoa.verification.service.VerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping("/members/verify")
    public ResponseEntity<String> verifyMember(@RequestBody VerificationReq req) {
         verificationService.memberVerification(req);
         return ResponseEntity.ok("가입되었습니다");
    }

}
