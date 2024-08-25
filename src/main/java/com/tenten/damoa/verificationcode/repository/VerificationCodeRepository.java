package com.tenten.damoa.verificationcode.repository;

import com.tenten.damoa.member.domain.Member;
import com.tenten.damoa.verificationcode.domain.VerificationCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByMember(Member member);
}
