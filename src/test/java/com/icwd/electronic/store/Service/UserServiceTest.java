package com.icwd.electronic.store.Service;

import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.dto.UserDto;
import com.icwd.electronic.store.entity.User;
import com.icwd.electronic.store.repository.userRepository;
import com.icwd.electronic.store.service.Impl.userServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    @Autowired
    private userServiceImpl userService;
    @Autowired
    @InjectMocks
    private ModelMapper modelMapper;
    @MockBean
    private userRepository userRepositoryl;
    private User user;
    @BeforeEach
    public void init(){

       user= User.builder()
                .userid(UUID.randomUUID().toString())
                .name("Nikhil")
                .email("rahul@gmail.com")
                .password("rahul@123")
                .gender("Male")
                .imagename("abc.jpg")
                .about("MEchanical Engg.")
                .build();
    }
    @Test
    public void createUserTest(){
        Mockito.when(userRepositoryl.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
    }

    @Test
    public void updateUserTest(){

        String userID="abc";
        UserDto userDto = UserDto.builder()
                .name("Sagar")
                .email("sagar@gmail.com")
                .password("sagar@123")
                .gender("Male")
                .imagename("abc.jpg")
                .about("Java Developer.")
                .build();


        Mockito.when(userRepositoryl.findById("abc")).thenReturn(Optional.of(user));
        Mockito.when(userRepositoryl.save(Mockito.any())).thenReturn(user);
        UserDto userDto1 = userService.updateUser(userDto, userID);
        System.out.println(userDto1.getName());
        Assertions.assertNotNull(userDto1);
    }
@Test
    public void getAllUserTest(){

       User user2= User.builder()
                .name("Shail")
                .email("rahul@gmail.com")
                .password("sahil@123")
                .gender("Male")
                .imagename("abc.jpg")
                .about("MEchanical Engg.")
                .build();
        User user3= User.builder()
                .name("Sagar")
                .email("sagar@gmail.com")
                .password("sagar@123")
                .gender("Male")
                .imagename("abc.jpg")
                .about("Front end Engineer")
                .build();
        User user4= User.builder()
                .name("Mayur")
                .email("Mayur@gmail.com")
                .password("Mayur@123")
                .gender("Male")
                .imagename("abc.jpg")
                .about(".net Engineer")
                .build();

        List<User> al = Arrays.asList(user,user2,user3,user4);
        Page<User> page= new PageImpl<>(al);
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(1,2, sort);
        Mockito.when(userRepositoryl.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
        Assertions.assertNotNull(allUser);
        Assertions.assertEquals(4,allUser.getContent().size());

    }
    @Test
    public void getUserByEmailTest(){
        String emailId="abc";
        Mockito.when(userRepositoryl.findByEmail(emailId)).thenReturn(user);

        UserDto userDto = userService.getUserByEmail(emailId);
        System.out.println(user.getName());
        Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"not matched");

    }
    @Test
    public void searchUserTest(){
        String namelike="n";
        Mockito.when(userRepositoryl.findById(namelike)).thenReturn(Optional.of(user));
        List<UserDto> userDtos = userService.searchUser(namelike);

       Assertions.assertNotNull(userDtos);
    }
    @Test
    public void getUserByIdTest(){
        String userId="abc";
        Mockito.when(userRepositoryl.findById(userId)).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserById(userId);
        System.out.println(userDto.getEmail());
        Assertions.assertNotNull(userDto);
    }
    @Test
    public void delteUserTest(){

       Mockito.when(userRepositoryl.findById(Mockito.anyString())).thenReturn(Optional.of(user));
       userService.deleteUser(Mockito.anyString());
       Mockito.verify(userRepositoryl,Mockito.times(1)).delete(user);
        //System.out.println(AppConstants.DELETED_SUCCESSFULLY);
    }
}

