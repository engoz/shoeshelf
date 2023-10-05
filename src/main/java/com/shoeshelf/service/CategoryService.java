package com.shoeshelf.service;

import com.shoeshelf.domain.Category;
import com.shoeshelf.dto.CategoryDto;
import com.shoeshelf.exceptions.CategoryNotFoundExceptions;
import com.shoeshelf.exceptions.InternelServerException;
import com.shoeshelf.repository.CategoryRepository;
import com.shoeshelf.util.CategoryDtoConverters;
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
            CategoryDto categoryDto = CategoryDtoConverters.convertDtoToCategory(category);
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
        CategoryDto categoryDto = CategoryDtoConverters.convertDtoToCategory(category);
        return categoryDto;
    }

    public CategoryDto createCategory(CategoryDto dto)  {
        if (dto == null)
            throw new NullPointerException();

        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());

        categoryRepository.save(category);

        dto.setId(category.getId());

        return dto;
    }

    public void deleteCategory(Integer id) {

        try {
            categoryRepository.deleteById(id);
        }catch(CategoryNotFoundExceptions ex){
            throw ex;
        }catch (Exception ex){
            throw new InternelServerException(ex);

        }

    }

    public CategoryDto update(CategoryDto dto) {
        Optional<Category> categoryOptional = categoryRepository.findById(dto.getId());
        if (categoryOptional.isEmpty()){
            throw new CategoryNotFoundExceptions("Category not found with id :" + dto.getId());
        }
        Category category = categoryOptional.get();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        categoryRepository.save(category);
        return dto;
    }

}
