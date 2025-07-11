package com.baio.money_minder.mappers;

import com.baio.money_minder.dtos.RegisterUserRequest;
import com.baio.money_minder.dtos.UpdateUserRequest;
import com.baio.money_minder.dtos.UserDto;
import com.baio.money_minder.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
