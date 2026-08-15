package com.icwd.electronic.store.controller;

import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.ImageResponse;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.dto.UserDto;
import com.icwd.electronic.store.dto.ApiResponse;
import com.icwd.electronic.store.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import com.icwd.electronic.store.service.userServiceI;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
public class userController {

    @Autowired
    private userServiceI userServiceI;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @param userDto
     * @param
     * @return
     * @author NikhPhalke
     * @apiNote Create A User
     * @since 1.0v
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userDto) {
        log.info("Entering The Request For Save  UserData");
        UserDto user = this.userServiceI.createUser(userDto);
        log.info("Completed The Request For Save UserData");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * @param userDto
     * @param userid
     * @return
     * @author NikhilPhalke
     * @apiNote Update A User
     * @since 1.0v
     */

    @PostMapping("/{userid}")
    public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userDto, @PathVariable String userid) {
        log.info("Entering The Request For Update UserData :{}", userid);
        UserDto userDto1 = this.userServiceI.updateUser(userDto, userid);
        log.info("Completed The Request For Update UserData :{}", userid);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    /**
     * @return
     * @author NikhilPhalke
     * @apiNote Get All Users
     * @since 1.0v
     **/

    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getalluser(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        log.info("Entering The Request For GetAllUsers");
        PageableResponse<UserDto> allUser = this.userServiceI.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed The Request For GetAllUsers");
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    /**
     * @param userid
     * @return
     * @author NikhilPhalke
     * @apiNote Get A Single User
     * @since 1.0v
     */

    @GetMapping("/{userid}")
    public ResponseEntity<UserDto> getuser(@PathVariable String userid) {
        log.info("Entering The Request For Get UserData :{}", userid);
        UserDto userById = this.userServiceI.getUserById(userid);
        log.info("Completed The Request For Get UserData :{}", userid);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    /**
     * @param userid
     * @return
     * @author NikhilPhalke
     * @apiNote Delete A User By UserId
     * @since 1.0v
     */
    @DeleteMapping("/{userid}")
    public ResponseEntity<ApiResponse> deleteuser(@PathVariable String userid) {
        log.info("Entering The Request For Delete The UserData :{}", userid);
        this.userServiceI.deleteUser(userid);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.DELETED_SUCCESSFULLY).status(HttpStatus.OK).success(true).build();
        log.info("Completed The Request For Delete The UserData :{}", userid);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @param email
     * @return
     * @author NikhilPhalke
     * @apiNote Get A Single User By Email
     * @since 1.0v
     */

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getuserbyemail(@PathVariable String email) {
        log.info("Entering The Request For Get The UserData :{}", email);
        UserDto userByEmail = this.userServiceI.getUserByEmail(email);
        log.info("Completed The Request For Get The UserData :{}", email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    /**
     * @param pattern
     * @return
     * @author NikhilPhalke
     * @apiNote Search User by containing any letter
     * @since 1.0v
     */
    @GetMapping("/searchuser/{pattern}")
    public ResponseEntity<List<UserDto>> searchuser(@PathVariable String pattern) {
        log.info("Entering The Request For Search The UserData :{}", pattern);
        List<UserDto> userDtos = this.userServiceI.searchUser(pattern);
        log.info("Completed The Request For Search The UserData :{}", pattern);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author NikhilPhalke
     * @apiNote Upload Image
     * @since 1.0v
     */

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image, @PathVariable String userId) throws IOException {
        log.info("Entering the Request for upload file with userId :{}",userId);
        String imageName = this.fileService.uploadFile(image, imageUploadPath);
        UserDto user = this.userServiceI.getUserById(userId);
        user.setImagename(imageName);
        UserDto userDto = this.userServiceI.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).message(AppConstants.UPLOAD).build();
        log.info("Completed the Request for upload file with userId :{}",userId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @return
     * @author NikhilPhalke
     * @apiNote Get Image
     * @since 1.0v
     */
    @GetMapping(value = "/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        log.info("Entering the Request for serve file with userId :{}",userId);
        UserDto user = this.userServiceI.getUserById(userId);
        InputStream resource = this.fileService.getResource(imageUploadPath, user.getImagename());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        log.info("Completed The Request For serve file with  userId :{}",userId);
        StreamUtils.copy(resource,response.getOutputStream());
    }
    @GetMapping("/welcome")
    public String getGreetMsg(){
      return "Welcome";
    }
}
