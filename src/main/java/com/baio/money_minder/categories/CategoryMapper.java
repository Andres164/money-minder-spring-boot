package com.baio.money_minder.categories;

import com.baio.money_minder.categories.dtos.CategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest categoryRequest);
    void update(CategoryRequest categoryRequest, @MappingTarget Category category);
}
