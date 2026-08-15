package com.icwd.electronic.store.Service;

import com.icwd.electronic.store.dto.CategoryDto;
import com.icwd.electronic.store.dto.ProductDto;
import com.icwd.electronic.store.entity.Category;
import com.icwd.electronic.store.entity.Product;
import com.icwd.electronic.store.service.Impl.productServiceImpl;
import com.icwd.electronic.store.repository.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class ProductServiceTest {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @InjectMocks
    private productServiceImpl productService;
    @MockBean
    private ProductRepo productRepo;
    private Product product;
    @BeforeEach
    public void init(){

       product=Product.builder()
                .productId(UUID.randomUUID().toString())
                .title("Mobile")
                .addeddate(new Date())
                .descountprice(2500.00)
                .live(true)
                .quantity(15L)
                .price(35000.00)
                .stock(true)
                .build();

    }
    @Test
    public void createProductTest(){
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.createProduct(modelMapper.map(product, ProductDto.class));
        System.out.println(productDto.getTitle());
        Assertions.assertEquals(productDto.getTitle(),productDto.getTitle(),"Mobile");
        Assertions.assertNotNull(productDto);


    }
    @Test
    public void updateProductTest(){
        String productId="abc";
        ProductDto productDto = ProductDto.builder()
                .title("Cricket-Bat")
                .live(true)
                .addeddate(new Date())
                .descountprice(150.00)
                .productimagename("MRF.png")
                .price(1500.00).build();

        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);

        ProductDto productDto1 = productService.updateProduct(productDto, productId);
        System.out.println(productDto1.getTitle());
        Assertions.assertNotNull(productDto1);
    }
    @Test
    public void deleteProductTest(){
        String productId="abc";
        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);
        Mockito.verify(productRepo,Mockito.times(1)).deleteById(productId);

    }

    @Test
    public void getSingleCategoryTest(){
        Mockito.when(productRepo.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        ProductDto productDto = productService.getProduct(Mockito.anyString());
        System.out.println(productDto.getTitle());
        Assertions.assertNotNull(productDto);
    }
}
