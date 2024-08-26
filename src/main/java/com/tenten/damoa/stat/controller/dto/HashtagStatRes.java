package com.tenten.damoa.stat.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Schema(description = "해시태그 통계 응답 객체")
@Builder
public record HashtagStatRes(
        @Schema(description = "일자")
        LocalDateTime date,

        @Schema(description = "값")
        Integer value
) {
}
