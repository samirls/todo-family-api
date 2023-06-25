package com.example.todofamilyapi.controller.mappers;

import com.example.todofamilyapi.controller.dtos.responses.InviteResponseDTO;
import com.example.todofamilyapi.entities.Invite;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InviteMapper {

     InviteResponseDTO fromEntity(Invite invite);

}
