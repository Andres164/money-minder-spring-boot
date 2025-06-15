package com.baio.money_minder.mappers;

import com.baio.money_minder.dtos.UserDto;
import com.baio.money_minder.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);
}
