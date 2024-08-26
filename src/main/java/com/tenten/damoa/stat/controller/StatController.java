package com.tenten.damoa.stat.controller;

import com.tenten.damoa.common.exception.ErrorResponse;
import com.tenten.damoa.stat.controller.dto.HashtagStatRes;
import com.tenten.damoa.stat.service.HashtagStatQueryService;
import com.tenten.damoa.stat.service.command.HashtagStatCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "통계")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class StatController {

    private final HashtagStatQueryService hashtagStatQueryService;

    @Operation(summary = "해시태그별 통계")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = HashtagStatRes.class)
                            ))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "입력된 시간 단위가 유효하지 않거나, " +
                            "입력된 지표 종류가 유효하지 않거나, " +
                            "입력된 기간의 종료일이 시작일보다 빠르거나, " +
                            "시간단위 조회 기간의 제한범위를 초과했거나, " +
                            "날짜단위 조회 기간의 제한범위를 초과했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/hashtags/stat")
    public ResponseEntity<List<HashtagStatRes>> getHashTagMetricsStat(
            @Parameter(description = "검색어", example = "tenten")
            @RequestParam @NotBlank String hashtag,
            @Parameter(description = "시간단위 `HOUR`/`DATE`", example = "DATE")
            @RequestParam @NotBlank String unit,
            @Parameter(description = "지표 `COUNT`/`VIEW_COUNT`/`LIKE_COUNT`/`SHARE_COUNT`", example = "VIEW_COUNT")
            @RequestParam @NotBlank String metric,
            @Parameter(description = "시작일", example = "2024-08-01T00:00:00")
            @RequestParam @NotNull LocalDateTime start,
            @Parameter(description = "종료일", example = "2024-08-31T23:59:59")
            @RequestParam @NotNull LocalDateTime end
    ) {
        HashtagStatCommand command = HashtagStatCommand.builder()
                .hashtag(hashtag)
                .unit(unit)
                .metric(metric)
                .start(start)
                .end(end)
                .build();

        List<HashtagStatRes> response = hashtagStatQueryService.getStatsForPeriod(command);

        return ResponseEntity.ok(response);
    }
}
