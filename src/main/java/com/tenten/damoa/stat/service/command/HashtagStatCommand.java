package com.tenten.damoa.stat.service.command;

import com.tenten.damoa.stat.domain.MetricsType;
import com.tenten.damoa.stat.domain.Period;
import com.tenten.damoa.stat.domain.TimeUnit;
import java.time.LocalDateTime;
import lombok.Builder;

public record HashtagStatCommand(
        String hashtag,
        TimeUnit unit,
        Period period,
        MetricsType metric
) {

    @Builder
    public HashtagStatCommand(String hashtag, String unit, LocalDateTime start, LocalDateTime end, String metric) {
        this(
                hashtag,
                TimeUnit.from(unit),
                Period.builder()
                        .unit(TimeUnit.from(unit))
                        .start(start)
                        .end(end)
                        .build(),
                MetricsType.from(metric)
        );
    }
}
