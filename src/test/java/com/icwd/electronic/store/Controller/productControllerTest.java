package com.icwd.electronic.store.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.CategoryDto;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.dto.ProductDto;
import com.icwd.electronic.store.dto.UserDto;
import com.icwd.electronic.store.entity.Product;
import com.icwd.electronic.store.entity.User;
import com.icwd.electronic.store.service.userServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.icwd.electronic.store.service.productServiceI;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class productControllerTest {

    @MockBean
    private productServiceI productServiceI;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    public void init() {
        product=Product.builder()
                .title("")
                .live(true)
                .price(20000.00)
                .quantity(10L)
                .stock(true)
                .addeddate(new Date())
                .productimagename("abc.jpg")
                .descountprice(2999.00)
                .build();


        productDto=ProductDto.builder()
                .addeddate(new Date())
                .title("Nokia")
                .live(true)
                .price(17000.00)
                .quantity(2l)
                .stock(true)
                .build();

    }

    private String convertObjTojsonString(Product product) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String valueAsString = mapper.writeValueAsString(product);
        return valueAsString;
    }

    @Test
    public void createProductTest() throws Exception {
        ProductDto productDto1 = modelMapper.map(product, ProductDto.class);
        Mockito.when(productServiceI.createProduct(Mockito.any())).thenReturn(productDto1);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/product/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjTojsonString(product))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    public void getallProductTest() throws Exception {
        PageableResponse<ProductDto> pagResponse = new PageableResponse<>();
        pagResponse.setContent(Arrays.asList(productDto, productDto));
        pagResponse.setLastPage(false);
        pagResponse.setPageNumber(10);
        pagResponse.setPageSize(2);
        pagResponse.setTotalElement(100l);
        Mockito.when(productServiceI.getAllProduct(Mockito.anyInt(), Mockito.anyInt()
                , Mockito.anyString(), Mockito.anyString())).thenReturn(pagResponse);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getProductTest() throws Exception {
        ProductDto productDto1 = modelMapper.map(product, ProductDto.class);
        String productId = "rahul@123";
        Mockito.when(productServiceI.getProduct(productId)).thenReturn(productDto1);
        mockMvc.perform(MockMvcRequestBuilders.get("/product/" + productId))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void deletProductTest() throws Exception {
        String productId = "rahul@123";
        Mockito.doNothing().when(this.productServiceI).deleteProduct(productId);
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/product/" + productId))
                .andDo(print()).andExpect(status().isOk());

        Mockito.verify(productServiceI, Mockito.times(1)).deleteProduct(productId);

    }
}
