package com.tenten.damoa.stat.domain;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;

public enum MetricsType {
    COUNT,
    VIEW_COUNT,
    LIKE_COUNT,
    SHARE_COUNT
    ;

    public static MetricsType from(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_METRICS_TYPE_EXCEPTION);
        }
    }
}
