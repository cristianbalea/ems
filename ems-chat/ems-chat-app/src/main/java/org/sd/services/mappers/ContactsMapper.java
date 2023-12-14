package org.sd.services.mappers;

import org.mapstruct.Mapper;
import org.sd.dtos.ChatContactDto;
import org.sd.entities.ContactEntity;

@Mapper
public interface ContactsMapper {
    ChatContactDto toDto(ContactEntity contactEntity);
}
