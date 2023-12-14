package org.sd.repositories;

import org.sd.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
    List<DeviceEntity> findAllByUserExternalId(UUID userExternalId);
    Optional<DeviceEntity> findByDeviceExternalId(UUID deviceExternalId);
    void deleteAllByUserExternalId(UUID userExternalId);
}
