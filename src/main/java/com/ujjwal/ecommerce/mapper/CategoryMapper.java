package com.ujjwal.ecommerce.mapper;

import com.ujjwal.ecommerce.model.Category;
import com.ujjwal.ecommerce.payload.dto.CategoryDTO;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .id(category.getId())
                .storeId(category.getStore()!=null?category.getStore().getId():null)
                .build();
    }
}
