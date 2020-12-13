package com.sda.serwisaukcyjnybackend.view.category;

import com.sda.serwisaukcyjnybackend.domain.category.Category;

public class CategoryMapper {

    public static CategoryDTO mapToCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .iconName(category.getIconName())
                .build();
    }
}
