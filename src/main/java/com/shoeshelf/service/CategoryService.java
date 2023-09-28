package com.shoeshelf.service;

import com.shoeshelf.domain.Category;
import com.shoeshelf.dto.CategoryDto;
import com.shoeshelf.exceptions.CategoryNotFoundExceptions;
import com.shoeshelf.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategory() throws CategoryNotFoundExceptions{
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        List<Category> allCategory = categoryRepository.findAll();
        if (allCategory.isEmpty()){
            throw new CategoryNotFoundExceptions("Category is empty");
        }
        for (Category category:allCategory) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setDescription(category.getDescription());
            categoryDto.setImageUrl(category.getImageUrl());
            categoryDtoList.add(categoryDto);
        }

        return categoryDtoList;
    }

    public CategoryDto getById(Integer id) throws CategoryNotFoundExceptions {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()){
            throw new CategoryNotFoundExceptions("Category not found with id :" + id);
        }
        Category category = categoryOptional.get();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setImageUrl(category.getImageUrl());
        return categoryDto;
    }

}
