package org.sd.repositories;

import org.sd.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByReceiverIdAndSenderIdOrderBySentAtAsc(UUID receiverId, UUID senderId);
    List<MessageEntity> findAllByReceiverId(UUID receiverId);
}
