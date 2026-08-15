package com.icwd.electronic.store.service.Impl;

import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.CategoryDto;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.dto.ProductDto;
import com.icwd.electronic.store.dto.UserDto;
import com.icwd.electronic.store.entity.Category;
import com.icwd.electronic.store.entity.Product;
import com.icwd.electronic.store.entity.User;
import com.icwd.electronic.store.exception.ResourceNotFoundException;
import com.icwd.electronic.store.helper.Helper;
import com.icwd.electronic.store.repository.categoryRepository;
import com.icwd.electronic.store.service.categoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class categoryServiceImpl implements categoryService {
    @Autowired
    private categoryRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Entering Dao Call For Save or Create The CategoryData");
        String string = UUID.randomUUID().toString();
        categoryDto.setCategoryId(string);
        Category category1 = mapper.map(categoryDto, Category.class);
        Category save = this.repository.save(category1);
        CategoryDto categoryDto1 = mapper.map(save, CategoryDto.class);
        log.info("Completed Dao Call For Save CategoryData ");
        return categoryDto1;

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        log.info("Entering Dao Call For Update The CategoryData : {}", categoryId);
        Category category = this.repository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + categoryId));
        //category.setCategoryId(categoryDto.getCategoryId());
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category save = this.repository.save(category);
        log.info("Completed Dao Call For Update The CategoryData : {}", categoryId);
        return this.mapper.map(save, CategoryDto.class);


    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(Integer pageNumber , Integer pageSize,String sortBy, String sortDir) {
        log.info("Entering Dao Call For Get All CategoryData ");
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber , pageSize,sort);
        Page<Category> all = this.repository.findAll(pageable);
        PageableResponse<CategoryDto> dtoPageableResponse = Helper.getPageableResponse(all, CategoryDto.class);
        log.info("Completed Dao Call For Get All CategoryData ");
        return Helper.getPageableResponse(all, CategoryDto.class);

    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        log.info("Entering Dao Call For Get The CategoryData :{}", categoryId);
        Category category = this.repository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryId));
        log.info("Completed Dao Call For Get The CategoryData :{}", categoryId);
        return this.mapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        log.info("Entering Dao Call For Delete CategoryData :{}",categoryId);
        Category category = this.repository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+ categoryId));
        log.info("Entering Dao Call For Delete  CategoryData :{}",categoryId);
        this.repository.deleteById(categoryId);


    }
}
