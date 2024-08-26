package com.tenten.damoa.stat.service;

import com.tenten.damoa.stat.controller.dto.HashtagStatRes;
import com.tenten.damoa.stat.repository.StatRepository;
import com.tenten.damoa.stat.service.command.HashtagStatCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagStatQueryService {

    private final StatRepository statRepository;

    public List<HashtagStatRes> getStatsForPeriod(HashtagStatCommand command) {
        return switch (command.metric()) {
            case COUNT -> statRepository.getPostCountForPeriod(command);
            default -> statRepository.getMetricsCountForPeriod(command);
        };
    }
}
