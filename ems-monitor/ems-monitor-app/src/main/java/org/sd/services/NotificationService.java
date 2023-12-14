package org.sd.services;

import lombok.RequiredArgsConstructor;
import org.sd.services.dtos.MessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendPrivateNotification(UUID userId, String content) {
        MessageDto message = MessageDto.builder().content(content).build();

        messagingTemplate.convertAndSend("/topic/user/" + userId.toString() + "/private-messages", message);
    }
}
