package ru.skillbox.zerone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.zerone.backend.model.entity.BlockHistory;

public interface BlockHistoryRepository extends JpaRepository<BlockHistory, Long> {
}
