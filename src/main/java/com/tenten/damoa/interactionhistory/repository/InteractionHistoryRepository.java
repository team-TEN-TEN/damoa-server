package com.tenten.damoa.interactionhistory.repository;

import com.tenten.damoa.interactionhistory.domain.InteractionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionHistoryRepository extends JpaRepository<InteractionHistory, Long> {

}
