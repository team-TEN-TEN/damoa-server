package com.tenten.damoa.stat.domain;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;

public enum TimeUnit {
    DATE,
    HOUR
    ;

    public static TimeUnit from(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_TIME_UNIT_EXCEPTION);
        }
    }
}
