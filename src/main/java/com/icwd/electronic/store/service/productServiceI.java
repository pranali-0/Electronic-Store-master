package com.icwd.electronic.store.service;

import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface productServiceI {

public ProductDto createProduct(ProductDto productDto);

public ProductDto updateProduct(ProductDto productDto, String productId);

public PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

public ProductDto getProduct(String productId);

public void deleteProduct(String productId);

PageableResponse<ProductDto> findByLiveTrue(Integer pageNumber,Integer pageSize, String sortBy,String sortDir);

PageableResponse<ProductDto> getAllLiveProduct (Integer pageNumber,Integer pageSize, String sortBy,String sortDir);
PageableResponse<ProductDto> getProductByTitle( Integer pageNumber,Integer pageSize, String sortBy,String sortDir,String pattern );

ProductDto createProductWithCategory(ProductDto productDto,String categoryId);
PageableResponse<ProductDto> getAllOfCategory(String categoryId,Integer pageNumber,Integer pageSize, String sortBy,String sortDir);
ProductDto updateCategory(String productId,String categoryId);

}
