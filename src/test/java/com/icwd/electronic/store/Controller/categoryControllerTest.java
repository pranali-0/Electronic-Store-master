package com.icwd.electronic.store.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icwd.electronic.store.dto.CategoryDto;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.dto.UserDto;
import com.icwd.electronic.store.entity.Category;
import com.icwd.electronic.store.entity.User;
import com.icwd.electronic.store.service.categoryService;
import com.icwd.electronic.store.service.userServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class categoryControllerTest {
    @MockBean
    private categoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private Category category;
    private CategoryDto  categoryDto;

    @BeforeEach
    public void init() {
    category=  Category.builder()
              .title("ABCD")
              .description("AAA BBB CCC")
              .coverImage("abc.png")
              .build();


      categoryDto=  CategoryDto.builder()
                .title("XYZ")
                .description("hdghjdghjd")
                .coverImage("abc.jpg")
                .build();

    }

    private String convertObjTojsonString(Category category) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String valueAsString = mapper.writeValueAsString(category);
        return valueAsString;
    }

    @Test
    public void createCategoryTest() throws Exception {
        CategoryDto categoryDto1 = modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto1);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/category/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjTojsonString(category))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }


    @Test
    public void getallCatogoriesTest() throws Exception {
        PageableResponse<CategoryDto> pagResponse = new PageableResponse<>();
        pagResponse.setContent(Arrays.asList(categoryDto, categoryDto));
        pagResponse.setLastPage(false);
        pagResponse.setPageNumber(5);
        pagResponse.setPageSize(2);
        pagResponse.setTotalElement(50l);
        Mockito.when(categoryService.getAllCategories(Mockito.anyInt(), Mockito.anyInt()
              ,Mockito.anyString(),Mockito.anyString())).thenReturn(pagResponse);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getCategoryTest() throws Exception {
        CategoryDto categoryDto1 = modelMapper.map(category, CategoryDto.class);
        String categoryId = "rahul@123";
        Mockito.when(this.categoryService.getSingleCategory(categoryId)).thenReturn(categoryDto1);
        mockMvc.perform(MockMvcRequestBuilders.get("/category/" + categoryId))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @Test
    public void deleteCategoryTest() throws Exception {
        String categoryId = "rahul@123";
        Mockito.doNothing().when(this.categoryService).deleteCategory(categoryId);
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/category/" + categoryId))
                .andDo(print()).andExpect(status().isOk());

        Mockito.verify(categoryService,Mockito.times(1)).deleteCategory(categoryId);
    }
}
