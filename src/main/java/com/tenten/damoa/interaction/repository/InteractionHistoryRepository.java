package com.tenten.damoa.interaction.repository;

import com.tenten.damoa.interaction.domain.InteractionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionHistoryRepository extends JpaRepository<InteractionHistory, Long> {

}
