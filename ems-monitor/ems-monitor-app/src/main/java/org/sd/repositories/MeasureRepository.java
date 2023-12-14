package org.sd.repositories;

import org.sd.entities.MeasureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MeasureRepository extends JpaRepository<MeasureEntity, Integer> {
    List<MeasureEntity> findAllByDeviceId(UUID deviceId);

    Page<MeasureEntity> findAllByDeviceIdAndTimestampBetween(
            UUID deviceId,
            LocalDateTime start,
            LocalDateTime end, Pageable pageable);
}
