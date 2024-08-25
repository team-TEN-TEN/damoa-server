package com.tenten.damoa.verificationcode.controller;

import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.verificationcode.dto.VerificationReq;
import com.tenten.damoa.verificationcode.dto.VerificationRes;
import com.tenten.damoa.verificationcode.service.VerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        VerificationRes res = verificationService.memberVerification(req);
        if (res.isSuccess()) {
            return ResponseEntity.ok(res.getMessage());
        } else {
            return ResponseEntity.badRequest().body(res.getMessage());
        }
    }

}
