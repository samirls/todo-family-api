package com.example.todofamilyapi.controller.mappers;

import com.example.todofamilyapi.controller.dtos.requests.UsersRequestDTO;
import com.example.todofamilyapi.controller.dtos.responses.UsersResponseDTO;
import com.example.todofamilyapi.entities.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

     Users toEntity(UsersRequestDTO usersRequestDTO);

     UsersResponseDTO fromEntity(Users users);

}
