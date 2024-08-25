package com.tenten.damoa.member.controller;

import com.tenten.damoa.common.exception.ErrorResponse;
import com.tenten.damoa.member.controller.request.LoginMemberReq;
import com.tenten.damoa.member.controller.response.LoginMemberRes;
import com.tenten.damoa.member.service.MemberLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "Member", description = "사용자 API")
public class MemberController {

    private final MemberLoginService memberLoginService;

    @PostMapping("/login")
    @Operation(summary = "사용자 로그인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 로그인 성공"),
        @ApiResponse(
            responseCode = "401",
            description = "1. 존재하지 않는 계정입니다.\n2. 비밀번호를 잘못 입력했습니다.",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        ),
        @ApiResponse(
            responseCode = "403",
            description = "서비스 회원이 아닙니다. 이메일 인증을 먼저 해주세요.",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    public ResponseEntity<LoginMemberRes> login(@RequestBody @Valid LoginMemberReq request) {
        LoginMemberRes response = memberLoginService.execute(request);
        return ResponseEntity.ok(response);
    }
}
