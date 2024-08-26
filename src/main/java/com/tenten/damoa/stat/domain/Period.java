package com.tenten.damoa.stat.domain;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Period {

    public static final int MAX_DATE_BASED_HOUR = 7;
    public static final int MAX_DATE_BASED_DATE = 30;

    private final  LocalDateTime start;
    private final LocalDateTime end;

    @Builder
    private Period(TimeUnit unit, LocalDateTime start, LocalDateTime end) {
        validate(unit, start, end);

        this.start = start;
        this.end = end;
    }

    private void validate(TimeUnit unit, LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new BusinessException(ErrorCode.INVALID_PERIOD_EXCEPTION);
        }

        switch (unit) {
            case HOUR -> {
                if (ChronoUnit.DAYS.between(start, end) > MAX_DATE_BASED_HOUR) {
                    throw new BusinessException(ErrorCode.PERIOD_HOUR_LIMIT_EXCEED_EXCEPTION);
                }
            }
            case DATE -> {
                if (ChronoUnit.DAYS.between(start, end) > MAX_DATE_BASED_DATE) {
                    throw new BusinessException(ErrorCode.PERIOD_DATE_LIMIT_EXCEED_EXCEPTION);
                }
            }
        }
    }
}
