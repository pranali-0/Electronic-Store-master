package com.icwd.electronic.store.controller;

import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.*;
import com.icwd.electronic.store.service.categoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.icwd.electronic.store.service.FileService;
import com.icwd.electronic.store.service.userServiceI;

@RestController
@RequestMapping("/category")
@Slf4j
public class categoryController {

    @Autowired
    private categoryService service;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @param categoryDto
     * @param
     * @return
     * @author NikhPhalke
     * @apiNote Create A Category
     * @since 1.0v
     */

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCat(@RequestBody CategoryDto categoryDto){
        log.info("Entering The Request For Save  UserData");
        CategoryDto category = this.service.createCategory(categoryDto);
        log.info("Completed The Request For Save UserData");
        return  new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @param categoryDto
     * @param categoryId
     * @return
     * @author NikhilPhalke
     * @apiNote Update A Category
     * @since 1.0v
     */
    @PostMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCat(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        log.info("Entering The Request For Update UserData :{}", categoryId);
        CategoryDto categoryDto1 = this.service.updateCategory(categoryDto, categoryId);
        log.info("Completed The Request For Update UserData :{}", categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }
    /**
     * @return
     * @author NikhilPhalke
     * @apiNote Get All Category
     * @since 1.0v
     **/
    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCat(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
             @RequestParam(value = "sortBy",defaultValue = "title" ,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
           ){
        log.info("Entering The Request For GetAllUsers");
        PageableResponse<CategoryDto> allCategories = this.service.getAllCategories(pageNumber, pageSize,sortBy,sortDir);
        log.info("Completed The Request For GetAllUsers");
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }
    /**
     * @param categoryId
     * @return
     * @author NikhilPhalke
     * @apiNote Get A Single category
     * @since 1.0v
     */
    @GetMapping("/{categoryId}")
     public ResponseEntity<CategoryDto> singleCategory(@PathVariable String categoryId){
        log.info("Entering The Request For Get UserData :{}", categoryId);
        CategoryDto singleCategory = this.service.getSingleCategory(categoryId);
        log.info("Completed The Request For Get UserData :{}", categoryId);
        return new ResponseEntity<>(singleCategory,HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return
     * @author NikhilPhalke
     * @apiNote Delete A Category by categoryId
     * @since 1.0v
     */
     @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
         log.info("Entering The Request For Delete The UserData :{}", categoryId);
         this.service.deleteCategory(categoryId);
         ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.DELETED_SUCCESSFULLY).success(true).status(HttpStatus.OK).build();
         log.info("Completed The Request For Delete The UserData :{}", categoryId);
         return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return
     * @author NikhilPhalke
     * @apiNote Upload Image
     * @since 1.0v
     */

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String categoryId) throws IOException {
        log.info("Entering the Request for upload file with categoryId :{}",categoryId);
        String imageName = this.fileService .uploadFile(image, imageUploadPath);
        CategoryDto category = this.service.getSingleCategory(categoryId);
        category.setCoverImage(imageName);
        CategoryDto categoryDto = this.service.updateCategory(category, categoryId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).message(AppConstants.UPLOAD).build();
        log.info("Completed the Request for upload file with categoryId :{}",categoryId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    /**
     * @param categoryId
     * @return
     * @author NikhilPhalke
     * @apiNote Get Image
     * @since 1.0v
     */
    @GetMapping(value = "/image/{categoryId}")
    public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        log.info("Entering the Request for serve file with categoryId :{}",categoryId);
        CategoryDto category = this.service.getSingleCategory(categoryId);
        InputStream resource = this.fileService.getResource(imageUploadPath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        log.info("Completed The Request For serve file with  categoryId :{}",categoryId);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
