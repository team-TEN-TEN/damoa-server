package com.tenten.damoa.member.controller;

import static com.tenten.damoa.common.exception.ErrorCode.ACCOUNT_CONFLICT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.GlobalExceptionHandler;
import com.tenten.damoa.member.controller.request.RegisterMemberReq;
import com.tenten.damoa.member.controller.response.RegisterMemberRes;
import com.tenten.damoa.member.domain.Member;
import com.tenten.damoa.member.domain.MemberRole;
import com.tenten.damoa.member.service.MemberRegisterService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @Mock
    private MemberRegisterService memberRegisterService;

    @InjectMocks
    private MemberController memberController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(memberController)
            .setControllerAdvice(GlobalExceptionHandler.class)
            .alwaysDo(print())
            .build();
    }

    @DisplayName("사용자가 회원가입을 성공하면 201을 반환한다.")
    @Test
    public void memberRegisterSuccessReturn201() throws Exception {
        // given
        RegisterMemberReq request = getRegisterMemberReq();
        Member member = getMember();
        RegisterMemberRes response = RegisterMemberRes.of(member);

        when(memberRegisterService.execute(any())).thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/v1/members/register")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("$.memberId").value(response.getMemberId()));
    }

    @DisplayName("사용자가 이미 사용중인 계정으로 회원가입을 하면 409를 반환한다.")
    @Test
    void memberAccountConflictRegisterReturn404() throws Exception {
        // given
        RegisterMemberReq request = getRegisterMemberReq();

        when(memberRegisterService.execute(any()))
            .thenThrow(new BusinessException(ACCOUNT_CONFLICT));

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/v1/members/register")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value("이미 사용중인 계정입니다."));
    }

    private RegisterMemberReq getRegisterMemberReq() {
        return RegisterMemberReq.builder()
            .account("tenten")
            .email("tenten@gmail.com")
            .password("password12!")
            .build();
    }

    private Member getMember() {
        return Member.builder()
            .account("tenten")
            .email("tenten@gmail.com")
            .password("password12!")
            .role(MemberRole.PRE_MEMBER)
            .joinedAt(LocalDateTime.now())
            .build();
    }
}