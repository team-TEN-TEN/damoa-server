package com.tenten.damoa.stat.repository;

import com.tenten.damoa.stat.controller.dto.HashtagStatRes;
import com.tenten.damoa.stat.service.command.HashtagStatCommand;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository {

    List<HashtagStatRes> getMetricsCount(HashtagStatCommand command);
}
