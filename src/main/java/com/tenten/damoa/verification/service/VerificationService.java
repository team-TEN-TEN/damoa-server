package com.tenten.damoa.verification.service;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.member.domain.Member;
import com.tenten.damoa.member.domain.MemberRole;
import com.tenten.damoa.member.repository.MemberRepository;
import com.tenten.damoa.verification.domain.VerificationCode;
import com.tenten.damoa.verification.dto.VerificationReq;
import com.tenten.damoa.verification.repository.VerificationCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final MemberRepository memberRepository;

    public VerificationService(VerificationCodeRepository verificationCodeRepository,
        MemberRepository memberRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
        this.memberRepository = memberRepository;
    }

    public void memberVerification(VerificationReq req) {
        String memberAccount = req.getAccount();
        Member member = memberRepository.findByAccount(memberAccount)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        VerificationCode verificationCode = verificationCodeRepository.findByMember(member)
            .orElseThrow(() -> new BusinessException(ErrorCode.CODE_NOT_FOUND));

        if (verificationCode.getCode().equals(req.getCode())) {
            handleSuccess(member);
        } else {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }


    private void handleSuccess(Member member) {
        member.updateRole(MemberRole.MEMBER);
        memberRepository.save(member);
    }
}
