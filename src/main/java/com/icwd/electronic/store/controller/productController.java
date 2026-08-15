package com.icwd.electronic.store.controller;

import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.*;
import com.icwd.electronic.store.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import com.icwd.electronic.store.service.productServiceI;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class productController {

    @Autowired
    private productServiceI productServiceI;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;



    /**
     * @param productDto
     * @param
     * @return
     * @author NikhPhalke
     * @apiNote Create A Category
     * @since 1.0v
     */

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        log.info("Entering The Request For Save  ProductData");
        ProductDto product = this.productServiceI.createProduct(productDto);
        log.info("Completed The Request For Save ProductData");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * @param productDto
     * @param productId
     * @return
     * @author NikhilPhalke
     * @apiNote Update A Category
     * @since 1.0v
     */

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId){
        log.info("Entering The Request For Update ProductData :{}", productId);
        ProductDto productDto1 = this.productServiceI.updateProduct(productDto, productId);
        log.info("Completed The Request For Update ProductData :{}", productId);
        return new ResponseEntity<>(productDto1,HttpStatus.OK);

    }

    /**
     * @return
     * @author NikhilPhalke
     * @apiNote Get All Category
     * @since 1.0v
     **/
    @GetMapping("/")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false)Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
           @RequestParam(value = "sortBy",defaultValue = "title" ,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
            ){
        log.info("Entering The Request For GetAllProduct");
        PageableResponse<ProductDto> allProduct = this.productServiceI.getAllProduct(pageNumber, pageSize,sortBy,sortDir);
        log.info("Completed The Request For GetAllProduct");
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    /**
     * @param productId
     * @return
     * @author NikhilPhalke
     * @apiNote Get A Single category
     * @since 1.0v
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){
        log.info("Entering The Request For Get ProductData :{}", productId);
        ProductDto product = this.productServiceI.getProduct(productId);
        log.info("Completed The Request For Get ProductData :{}", productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    /**
     * @param productId
     * @return
     * @author NikhilPhalke
     * @apiNote Delete A User By productId
     * @since 1.0v
     */

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable String productId) {
        log.info("Entering The Request For Delete ProductData :{}", productId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.DELETED_SUCCESSFULLY).status(HttpStatus.OK).success(true).build();
        this.productServiceI.deleteProduct(productId);
        log.info("Completed The Request For Delete ProductData :{}", productId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
    /**
     * @param productDto
     * @param productId
     * @return
     * @author NikhilPhalke
     * @apiNote Update A Product
     * @since 1.0v
     */


    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable String productId, @RequestBody ProductDto productDto) {
        log.info("Entering The Request For Update ProductData :{}", productId);
        ProductDto productDto1 = this.productServiceI.updateProduct(productDto, productId);
        log.info("Completed The Request For Update ProductData :{}", productId);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }
    /**
     * @return
     * @author NikhilPhalke
     * @apiNote Get All Live True Products
     * @since 1.0v
     **/



    @GetMapping("/trueliveproducts")
    public ResponseEntity<PageableResponse> findAllLiveTrueProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction
    ) {
        log.info("Entering The Request For GetAllLiveTrueProduct");
        PageableResponse<ProductDto> allLIveProducts = this.productServiceI.findByLiveTrue(pageNumber, pageSize, sortBy, direction);
        log.info("Completed The Request For GetAllLiveTrueProduct");
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);
    }
    /**
     * @return
     * @author NikhilPhalke
     * @apiNote Get All Live Products
     * @since 1.0v
     **/

    @GetMapping("/liveproducts")
    public ResponseEntity<PageableResponse> getLiveProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        log.info("Entering The Request For GetAllLiveProducts");
        PageableResponse<ProductDto> allLIveProducts = this.productServiceI.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed The Request For GetAllLiveProducts");
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);

    }
    /**
     * @return
     * @author NikhilPhalke
     * @apiNote Get All Products By Title
     * @since 1.0v
     **/

    @GetMapping("/keyword/{pattern}")
    public ResponseEntity<PageableResponse> getProductByTitle(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
            ,@PathVariable String pattern ){
        log.info("Entering The Request For GetAllProductsByTitle");
        PageableResponse<ProductDto> productByTitle = this.productServiceI.getProductByTitle( pageNumber, pageSize, sortBy, sortDir,pattern);
        log.info("Completed The Request For GetAllProductsByTitle");
        return new ResponseEntity<>(productByTitle,HttpStatus.OK);
    }
    /**
     * @param productId
     * @return
     * @author NikhilPhalke
     * @apiNote Upload Image
     * @since 1.0v
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String productId) throws IOException {
        log.info("Entering the Request for upload file with productId :{}",productId);
        String imageName = this.fileService .uploadFile(image, imageUploadPath);
        ProductDto product = this.productServiceI.getProduct(productId);
        product.setProductimagename(imageName);
        ProductDto productDto = this.productServiceI.updateProduct(product, productId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).message(AppConstants.UPLOAD).build();
        log.info("Completed the Request for upload file with productId :{}",productId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    /**
     * @param productId
     * @return
     * @author NikhilPhalke
     * @apiNote Get Image
     * @since 1.0v
     */
    @GetMapping(value = "/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        log.info("Entering the Request for serve file with productId :{}",productId);
        ProductDto product = this.productServiceI.getProduct(productId);
        InputStream resource = this.fileService.getResource(imageUploadPath, product.getProductimagename());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        log.info("Completed The Request For serve file with  productId :{}",productId);
        StreamUtils.copy(resource,response.getOutputStream());
    }



}
