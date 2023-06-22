package com.example.todofamilyapi.controller.mappers;

import com.example.todofamilyapi.annotations.EncodedMapping;
import com.example.todofamilyapi.controller.dtos.requests.SignupUserRequestDTO;
import com.example.todofamilyapi.controller.dtos.requests.UsersRequestDTO;
import com.example.todofamilyapi.controller.dtos.responses.UsersResponseDTO;
import com.example.todofamilyapi.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersMapper {

     @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
     Users toEntity(UsersRequestDTO usersRequestDTO);

     @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
     Users toEntity(SignupUserRequestDTO signupUserRequestDTO);

     UsersResponseDTO fromEntity(Users users);

}
