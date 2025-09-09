package com.baio.money_minder.users;

import com.baio.money_minder.users.dtos.RegisterUserRequest;
import com.baio.money_minder.users.dtos.UpdateUserRequest;
import com.baio.money_minder.users.dtos.UserDto;
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
