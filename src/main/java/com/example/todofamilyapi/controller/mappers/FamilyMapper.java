package com.example.todofamilyapi.controller.mappers;

import com.example.todofamilyapi.controller.dtos.requests.FamilyRequestDTO;
import com.example.todofamilyapi.controller.dtos.responses.FamilyResponseDTO;
import com.example.todofamilyapi.entities.Family;
import com.example.todofamilyapi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = UserService.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FamilyMapper {

    Family toEntity(FamilyRequestDTO familyRequestDTO);

    FamilyResponseDTO fromEntity(Family family);

}
