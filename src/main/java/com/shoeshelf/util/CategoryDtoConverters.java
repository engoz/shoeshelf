package com.shoeshelf.util;

import com.shoeshelf.domain.Category;
import com.shoeshelf.dto.CategoryDto;

public class CategoryDtoConverters {



    public static  Category convertDtoToCategory(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        return category;
    }

    public static  CategoryDto convertDtoToCategory(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setImageUrl(category.getImageUrl());
        return categoryDto;
    }


}
