package org.sd.controllers;

import lombok.RequiredArgsConstructor;
import org.sd.api.ChatApi;
import org.sd.dtos.*;
import org.sd.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController implements ChatApi {
    private final ChatService chatService;

    @Override
    public ResponseEntity<List<ChatContactDto>> getContacts(GetChatInformationRequest getChatInformationRequest) {
        return ResponseEntity.ok(chatService.getContacts(getChatInformationRequest));
    }

    @Override
    public ResponseEntity<List<ChatMessageDto>> getConversation(GetChatInformationRequest getChatInformationRequest) {
        return ResponseEntity.ok(chatService.getConversation(getChatInformationRequest));
    }

    @Override
    public ResponseEntity<ResponseMessage> sendMessage(SendMessageRequest sendMessageRequest) {
        return ResponseEntity.ok(chatService.saveMessage(sendMessageRequest));
    }
}
