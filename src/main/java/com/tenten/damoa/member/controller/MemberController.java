package com.tenten.damoa.member.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.tenten.damoa.common.exception.ErrorResponse;
import com.tenten.damoa.member.controller.request.RegisterMemberReq;
import com.tenten.damoa.member.controller.response.RegisterMemberRes;
import com.tenten.damoa.member.service.MemberRegisterService;
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

    private final MemberRegisterService memberRegisterService;

    @PostMapping("/register")
    @Operation(summary = "사용자 회원가입")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "사용자 회원가입 성공"),
        @ApiResponse(
            responseCode = "409",
            description = "이미 사용중인 계정입니다.",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    public ResponseEntity<RegisterMemberRes> register(
        @RequestBody @Valid RegisterMemberReq request
    ) {
        RegisterMemberRes response = memberRegisterService.execute(request);
        return ResponseEntity.status(CREATED).body(response);
    }
}
