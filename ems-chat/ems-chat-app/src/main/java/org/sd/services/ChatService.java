package org.sd.services;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.sd.dtos.*;
import org.sd.entities.ContactEntity;
import org.sd.entities.MessageEntity;
import org.sd.repositories.ContactsRepository;
import org.sd.repositories.MessageRepository;
import org.sd.services.mappers.ContactsMapper;
import org.sd.services.mappers.MessageMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ContactsRepository contactsRepository;
    private final ContactsMapper contactsMapper = Mappers.getMapper(ContactsMapper.class);
    private final MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);


    public List<ChatContactDto> getContacts(GetChatInformationRequest getChatInformationRequest) {
        List<MessageEntity> allByReceiverId = messageRepository.findAllByReceiverId(getChatInformationRequest.getUserExternalId());

        List<UUID> contactsList = new ArrayList<>();
        for (MessageEntity m : allByReceiverId) {
            if (!contactsList.contains(m.getSenderId())) {
                contactsList.add(m.getSenderId());
            }
        }

        List<ChatContactDto> contacts = new ArrayList<>();
        for (UUID id : contactsList) {
            Optional<ContactEntity> contact = contactsRepository.findByUserExternalId(id);
            contact.ifPresent(contactEntity -> contacts.add(contactsMapper.toDto(contactEntity)));
        }

        return contacts;
    }

    public List<ChatMessageDto> getConversation(GetChatInformationRequest getChatInformationRequest) {
        return messageRepository.findAllByReceiverIdAndSenderIdOrderBySentAtAsc(
                getChatInformationRequest.getUserExternalId(),
                getChatInformationRequest.getContactExternalId()
        ).stream().map(messageMapper::toDto).toList();
    }

    public ResponseMessage saveMessage(SendMessageRequest sendMessageRequest) {
        messageRepository.save(MessageEntity.builder()
                .receiverId(sendMessageRequest.getReceiverId())
                .senderId(sendMessageRequest.getSenderId())
                .content(sendMessageRequest.getContent())
                .seen(false)
                .sentAt(LocalDateTime.now())
                .build());

        return ResponseMessage.builder().message("Message sent!").build();
    }
}
