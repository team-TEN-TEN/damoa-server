package com.tenten.damoa.verificationcode.service;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.member.domain.Member;
import com.tenten.damoa.member.domain.MemberRole;
import com.tenten.damoa.member.repository.MemberRepository;
import com.tenten.damoa.verificationcode.domain.VerificationCode;
import com.tenten.damoa.verificationcode.dto.VerificationReq;
import com.tenten.damoa.verificationcode.dto.VerificationRes;
import com.tenten.damoa.verificationcode.repository.VerificationCodeRepository;
import java.util.Optional;
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

    public VerificationRes memberVerification(VerificationReq req) {
        String memberAccount = req.getAccount();
        Member member = memberRepository.findByAccount(memberAccount)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        VerificationCode verificationCode = verificationCodeRepository.findByMember(member)
            .orElseThrow(() -> new BusinessException(ErrorCode.CODE_NOT_FOUND));

        if (verificationCode.getCode().equals(req.getCode())) {
            handleSuccess(member);
            return new VerificationRes(true, "가입되었습니다");
        } else {
            return new VerificationRes(false, "인증 코드가 일치하지 않습니다");
        }
    }


    private void handleSuccess(Member member) {
        member.updateRole(MemberRole.MEMBER);
        memberRepository.save(member);
    }
}
