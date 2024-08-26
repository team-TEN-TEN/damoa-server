package com.tenten.damoa.verification.repository;

import com.tenten.damoa.member.domain.Member;
import java.util.Optional;
import com.tenten.damoa.verification.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByMember(Member member);
}
