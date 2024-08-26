package com.tenten.damoa.stat.controller.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record HashtagStatRes(
        LocalDateTime date,
        Integer value
) {
}
