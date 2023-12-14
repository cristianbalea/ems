package org.sd.api;

import jakarta.validation.Valid;
import org.sd.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Validated
public interface ChatApi {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/chat/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ChatContactDto>> getContacts(@RequestBody @Valid GetChatInformationRequest getChatInformationRequest);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/chat/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ChatMessageDto>> getConversation(@RequestBody @Valid GetChatInformationRequest getChatInformationRequest);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/chat/send", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseMessage> sendMessage(@RequestBody @Valid SendMessageRequest sendMessageRequest);
}
