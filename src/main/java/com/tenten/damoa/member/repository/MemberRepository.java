package com.tenten.damoa.member.repository;

import com.tenten.damoa.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByAccount(String account);

    Optional<Member> findByAccount(String account);
}
