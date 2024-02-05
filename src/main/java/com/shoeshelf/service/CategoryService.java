package com.shoeshelf.service;

import com.shoeshelf.domain.Category;
import com.shoeshelf.dto.category.CategoryCreateDto;
import com.shoeshelf.dto.category.CategoryDto;
import com.shoeshelf.dto.category.CategoryUpdateDto;
import com.shoeshelf.exceptions.CategoryExistException;
import com.shoeshelf.exceptions.CategoryNotFoundExceptions;
import com.shoeshelf.exceptions.InternelServerException;
import com.shoeshelf.repository.CategoryRepository;
import com.shoeshelf.util.CategoryDtoConverters;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<CategoryDto> getAllCategory() throws CategoryNotFoundExceptions{
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        List<Category> allCategory = repository.findAll();
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
        Optional<Category> categoryOptional = repository.findById(id);
        if (categoryOptional.isEmpty()){
            throw new CategoryNotFoundExceptions("Category not found with id :" + id);
        }
        Category category = categoryOptional.get();
        CategoryDto categoryDto = CategoryDtoConverters.convertDtoToCategory(category);
        return categoryDto;
    }

    public CategoryDto createCategory(CategoryCreateDto dto)  {
        if (dto == null)
            throw new NullPointerException();

        var nameExists = repository.findByCategoryName(dto.getCategoryName());

        if (nameExists != null)
            throw new CategoryExistException("Category Exist");

        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());

        repository.save(category);

        return CategoryDtoConverters.convertDtoToCategory(category);

    }

    public CategoryDto update(CategoryUpdateDto dto) {
        Optional<Category> categoryOptional = repository.findById(dto.getId());
        if (categoryOptional.isEmpty()){
            throw new CategoryNotFoundExceptions("Category not found with id :" + dto.getId());
        }
        Category category = categoryOptional.get();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        category.setModifiedDate(LocalDateTime.now());
        repository.save(category);
        return CategoryDtoConverters.convertDtoToCategory(category);
    }

    public void deleteCategory(Integer id) {

        try {
            repository.deleteById(id);
        }catch(CategoryNotFoundExceptions ex){
            throw ex;
        }catch (Exception ex){
            throw new InternelServerException(ex);
        }

    }



}
