package com.tenten.damoa.member.repository;

import com.tenten.damoa.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
