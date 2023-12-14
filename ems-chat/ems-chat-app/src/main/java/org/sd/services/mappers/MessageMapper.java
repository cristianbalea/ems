package org.sd.services.mappers;

import org.mapstruct.Mapper;
import org.sd.dtos.ChatMessageDto;
import org.sd.entities.MessageEntity;

@Mapper
public interface MessageMapper {
    ChatMessageDto toDto(MessageEntity message);
}
