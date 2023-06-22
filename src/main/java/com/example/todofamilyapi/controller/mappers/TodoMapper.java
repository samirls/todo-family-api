package com.example.todofamilyapi.controller.mappers;

import com.example.todofamilyapi.controller.dtos.requests.TodoRequestDTO;
import com.example.todofamilyapi.controller.dtos.responses.TodoResponseDTO;
import com.example.todofamilyapi.entities.Todo;
import com.example.todofamilyapi.services.FamilyService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = FamilyService.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {

    @Mapping(source = "familyId", target = "family")
    Todo toEntity(TodoRequestDTO todo);

    TodoResponseDTO fromEntity(Todo todo);

}
