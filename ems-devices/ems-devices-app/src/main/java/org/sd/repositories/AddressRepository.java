package org.sd.repositories;

import org.sd.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {
    Optional<AddressEntity> findByDevice_Id(int deviceId);
    void deleteByDevice_UserExternalId(UUID deviceExternalId);
}
