package com.tenten.damoa.stat.controller;

import com.tenten.damoa.stat.controller.dto.HashtagStatRes;
import com.tenten.damoa.stat.service.HashtagStatQueryService;
import com.tenten.damoa.stat.service.command.HashtagStatCommand;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class StatController {

    private final HashtagStatQueryService hashtagStatQueryService;

    @GetMapping("/hashtags/stat")
    public ResponseEntity<List<HashtagStatRes>> getHashTagMetricsStat(
            @RequestParam @NotBlank String hashtag,
            @RequestParam @NotBlank String unit,
            @RequestParam @NotBlank String metric,
            @RequestParam @NotNull LocalDateTime start,
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
