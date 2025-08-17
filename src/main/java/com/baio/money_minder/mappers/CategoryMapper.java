package com.baio.money_minder.mappers;

import com.baio.money_minder.dtos.CategoryRequest;
import com.baio.money_minder.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest categoryRequest);
    Category update(CategoryRequest categoryRequest, @MappingTarget Category category);
}
