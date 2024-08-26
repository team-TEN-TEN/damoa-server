package com.tenten.damoa.member.service;

import static com.tenten.damoa.common.exception.ErrorCode.ACCOUNT_CONFLICT;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.member.controller.request.RegisterMemberReq;
import com.tenten.damoa.member.controller.response.RegisterMemberRes;
import com.tenten.damoa.member.domain.Member;
import com.tenten.damoa.member.repository.MemberRepository;
import com.tenten.damoa.verification.domain.VerificationCode;
import com.tenten.damoa.verification.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberRegisterService {

    private final MemberRepository memberRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterMemberRes execute(RegisterMemberReq request) {
        verifyAccount(request.getAccount());

        Member member = request.toMember(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(member);

        VerificationCode verificationCode = VerificationCode.create(member);
        verificationCodeRepository.save(verificationCode);

        return RegisterMemberRes.of(member);
    }

    private void verifyAccount(String account) {
        if (memberRepository.existsByAccount(account)) {
            throw new BusinessException(ACCOUNT_CONFLICT);
        }
    }
}
