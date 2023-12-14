package org.sd.repositories;

import org.sd.entities.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactsRepository extends JpaRepository<ContactEntity, Integer> {
    Optional<ContactEntity> findByUserExternalId(UUID userExternalId);
}
