package org.sd.repositories;

import org.sd.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUserEntityByUserExternalId(UUID userExternalId);
    Optional<UserEntity> findByCnp(String cnp);
}
