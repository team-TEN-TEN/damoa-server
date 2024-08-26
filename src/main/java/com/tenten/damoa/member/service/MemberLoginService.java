package com.tenten.damoa.member.service;

import static com.tenten.damoa.common.exception.ErrorCode.ACCOUNT_UNAUTHORIZED;
import static com.tenten.damoa.common.exception.ErrorCode.PASSWORD_UNAUTHORIZED;

import com.tenten.damoa.common.config.auth.JwtToken;
import com.tenten.damoa.common.config.auth.JwtUtil;
import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.member.controller.request.LoginMemberReq;
import com.tenten.damoa.member.controller.response.LoginMemberRes;
import com.tenten.damoa.member.domain.Member;
import com.tenten.damoa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberLoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginMemberRes execute(LoginMemberReq request) {
        Member member = findMember(request);
        member.verifyMemberRole();

        JwtToken token = jwtUtil.createToken(member);

        return LoginMemberRes.of(member, token);
    }

    private Member findMember(LoginMemberReq request) {
        Member member = memberRepository
            .findByAccount(request.getAccount())
            .orElseThrow(() -> new BusinessException(ACCOUNT_UNAUTHORIZED));
        verifyPassword(request.getPassword(), member.getPassword());

        return member;
    }

    private void verifyPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BusinessException(PASSWORD_UNAUTHORIZED);
        }
    }
}
