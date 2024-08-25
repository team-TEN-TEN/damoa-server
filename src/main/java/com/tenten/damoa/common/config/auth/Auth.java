package com.tenten.damoa.common.config.auth;

import com.tenten.damoa.member.domain.MemberRole;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    MemberRole role() default MemberRole.MEMBER;
}
