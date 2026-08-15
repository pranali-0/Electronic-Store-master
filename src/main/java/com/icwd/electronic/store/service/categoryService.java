package com.icwd.electronic.store.service;

import com.icwd.electronic.store.dto.CategoryDto;

import com.icwd.electronic.store.dto.PageableResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface categoryService {

    // create category
    public CategoryDto createCategory(CategoryDto categoryDto);
    // updateCategory
    public CategoryDto  updateCategory (CategoryDto categoryDto, String categoryId );
    // getAllCategories
    public PageableResponse<CategoryDto> getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    //getSingleCategory
    public CategoryDto getSingleCategory(String categoryId);
    //deleteCategory
    public void deleteCategory(String categoryId);
}
