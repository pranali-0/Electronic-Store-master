package com.icwd.electronic.store.Service;

import com.icwd.electronic.store.dto.CategoryDto;
import com.icwd.electronic.store.entity.Category;
import com.icwd.electronic.store.service.Impl.categoryServiceImpl;
import com.icwd.electronic.store.repository.categoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @InjectMocks
    private categoryServiceImpl categoryService;

    @MockBean
    private categoryRepository categoryRepository;

    private Category category;
    @BeforeEach
    public void init(){

        category = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .title("Cricket")
                .description("Cricket is a bat-and-ball game")
                .coverImage("cover.jpg")
                .build();
    }
    @Test
    public void createCategoryTest(){
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = categoryService.createCategory(modelMapper.map(category, CategoryDto.class));
        System.out.println(categoryDto.getTitle());
        Assertions.assertEquals(category.getTitle(),categoryDto.getTitle(),"Cricket");
        Assertions.assertNotNull(categoryDto);


    }
    @Test
    public void updateCategoryTest(){
        String categoryId="abc";
        CategoryDto categoryDto = CategoryDto
                .builder()
                .title("Kabaddi")
                .description("U Mumba")
                .coverImage("U-Mumba.jpg")
                .build();
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
        System.out.println(categoryDto1.getTitle());
        Assertions.assertNotNull(categoryDto1);
    }
    @Test
    public void deleteCategory(){
        String categoryId="abc";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryId);
        Mockito.verify(categoryRepository,Mockito.times(1)).deleteById(categoryId);

    }

    @Test
    public void getSingleCategoryTest(){
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        CategoryDto categoryDto = categoryService.getSingleCategory(Mockito.anyString());
        System.out.println(categoryDto.getTitle());
        Assertions.assertNotNull(categoryDto);
    }



}
