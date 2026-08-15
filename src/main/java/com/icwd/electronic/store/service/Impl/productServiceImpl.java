package com.icwd.electronic.store.service.Impl;

import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.dto.ProductDto;
import com.icwd.electronic.store.entity.Category;
import com.icwd.electronic.store.entity.Product;
import com.icwd.electronic.store.exception.ResourceNotFoundException;
import com.icwd.electronic.store.helper.Helper;
import com.icwd.electronic.store.repository.ProductRepo;
import com.icwd.electronic.store.service.productServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import com.icwd.electronic.store.repository.categoryRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Date;

import java.util.UUID;

@Service
@Slf4j
public class productServiceImpl implements productServiceI {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private categoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Entering Dao Call For Save or Create The ProductData");
        String productID = UUID.randomUUID().toString();
        productDto.setProductId(productID);
        productDto.setAddeddate(new Date());
        Product product = this.modelMapper.map(productDto, Product.class);
        Product product1 = this.productRepo.save(product);
        log.info("Completed Dao Call For Save ProductData ");
        return this.modelMapper.map(product1, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        log.info("Entering Dao Call For Update The ProductData : {}", productId);
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + productId));
        product.setTitle(productDto.getTitle());
        product.setLive(productDto.getLive());
        product.setAddeddate(productDto.getAddeddate());
        product.setDescountprice(productDto.getDescountprice());
        product.setQuantity(productDto.getQuantity());
        product.setProductimagename(productDto.getProductimagename());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        Product product1 = this.productRepo.save(product);
        log.info("Completed Dao Call For Update The ProductData : {}", productId);
        return this.modelMapper.map(product1, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct( Integer pageNumber,  Integer pageSize,String sortBy,String sortDir ) {
        log.info("Entering Dao Call For Get All ProductData ");
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber , pageSize,sort);
        Page<Product> all = this.productRepo.findAll(pageable);
        PageableResponse<ProductDto> dtoPageableResponse = Helper.getPageableResponse(all, ProductDto.class);
        log.info("Completed Dao Call For Get All ProductData ");
        return dtoPageableResponse;
    }

    @Override
    public ProductDto getProduct(String productId) {
        log.info("Entering Dao Call For Get The ProductData :{}", productId);
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + productId));
        log.info("Completed Dao Call For Get The ProductData :{}", productId);
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        log.info("Entering Dao Call For Delete UserData :{}",productId);
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        log.info("Entering Dao Call For Delete  UserData :{}",productId);
        this.productRepo.delete(product);
    }

    @Override
    public PageableResponse<ProductDto> findByLiveTrue(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        log.info("Entering Dao Call For Get All Live True ProductData ");
        PageRequest pageable = PageRequest.of(pageNumber , pageSize,sort);
        Page<Product> all = this.productRepo.findByLiveTrue(pageable);
        PageableResponse<ProductDto> dtoPageableResponse = Helper.getPageableResponse(all, ProductDto.class);
        log.info("Completed Dao Call For Get All Live True ProductData ");
        return dtoPageableResponse;
    }



    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        log.info("Entering Dao Call For Get All Live ProductData ");
        PageRequest pageable = PageRequest.of(pageNumber , pageSize,sort);
        Page<Product> all = this.productRepo.findAll(pageable);
        PageableResponse<ProductDto> dtoPageableResponse = Helper.getPageableResponse(all, ProductDto.class);
        log.info("Completed Dao Call For Get All Live ProductData ");
        return dtoPageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getProductByTitle(Integer pageNumber, Integer pageSize, String sortBy, String sortDir,String pattern) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        log.info("Entering Dao Call For Get All ProductData By Title ");
        PageRequest pageable = PageRequest.of(pageNumber , pageSize,sort);
        Page<Product> all = this.productRepo.findByTitleContaining(pageable,pattern);
        PageableResponse<ProductDto> dtoPageableResponse = Helper.getPageableResponse(all, ProductDto.class);
        log.info("Completed Dao Call For Get All ProductData By Title ");
        return dtoPageableResponse;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        log.info("Entering Dao Call For Save or Create The ProductData By Using Category");
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        Product product = this.modelMapper.map(productDto, Product.class);
        Date date = new Date();
        String pid = UUID.randomUUID().toString();
        product.setProductId(pid);
        product.setAddeddate(date);
        product.setCategories(category);
        Product newProduct = this.productRepo.save(product);
        log.info("Completed Dao Call For Save ProductData By Using Category ");
        return  modelMapper.map(newProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Entering Dao Call For Get All ProductData ");
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber , pageSize,sort);
        Page<Product> all = this.productRepo.findByCategories(category,pageable);
        PageableResponse<ProductDto> dtoPageableResponse = Helper.getPageableResponse(all, ProductDto.class);
        log.info("Completed Dao Call For Get All ProductData ");
        return dtoPageableResponse;
    }


    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        log.info("Entering Dao Call For Update The Category : {}", categoryId);
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        product.setCategories(category);
        Product save = this.productRepo.save(product);
        log.info("Completed Dao Call For Update The Product : {}", productId);
        return  modelMapper.map(save, ProductDto.class);
    }
}
