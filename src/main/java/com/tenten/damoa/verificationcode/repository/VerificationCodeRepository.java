package com.tenten.damoa.verificationcode.repository;

import com.tenten.damoa.verificationcode.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

}
