package com.tenten.damoa.verification.repository;

import com.tenten.damoa.verification.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

}
