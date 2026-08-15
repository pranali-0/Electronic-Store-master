package com.icwd.electronic.store.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icwd.electronic.store.controller.userController;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.dto.UserDto;
import com.icwd.electronic.store.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.icwd.electronic.store.service.userServiceI;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class userControllerTest {
    @MockBean
    private userServiceI userServiceI;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void init() {
        user = User.builder()
                .userid(UUID.randomUUID().toString())
                .name("Mayur Patil")
                .email("mayu@gamil.com")
                .password("mayu@123")
                .gender("Male")
                .imagename("abc.png")
                .about("IT Engineer")
                .build();


        UserDto userDto = UserDto.builder()
                .name("Shital")
                .email("shital@gmail.com")
                .about("I am singer")
                .gender("Male")
                .imagename("abc.png")
                .password("shi@123")
                .build();

        UserDto userDto2 = UserDto.builder()
                .name("Akshay")
                .email("ak@gmail.com")
                .about("i am Mechanical Engineer.")
                .gender("Male")
                .imagename("mech.png")
                .password("as@123")
                .build();
    }

    private String convertObjTojsonString(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String valueAsString = mapper.writeValueAsString(user);
        return valueAsString;
    }

    @Test
    public void createuserTest() throws Exception {
        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjTojsonString(user))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void getalluserTest() throws Exception {
        PageableResponse<UserDto> pagResponse = new PageableResponse<>();
        pagResponse.setContent(Arrays.asList(userDto, userDto));
        pagResponse.setLastPage(false);
        pagResponse.setPageNumber(10);
        pagResponse.setPageSize(2);
        pagResponse.setTotalElement(100l);
        Mockito.when(userServiceI.getAllUser(Mockito.anyInt(), Mockito.anyInt()
                , Mockito.anyString(), Mockito.anyString())).thenReturn(pagResponse);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getuserTest() throws Exception {
        UserDto dto = modelMapper.map(user, UserDto.class);
        String userId = "rahul@123";
        Mockito.when(userServiceI.getUserById(userId)).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + userId))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void getuserbyemailTest() throws Exception {
        UserDto dto = modelMapper.map(user, UserDto.class);
        String email = "ak@gmail.com";
        Mockito.when(userServiceI.getUserByEmail(email)).thenReturn(dto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/email/" + email))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void searchuserTest() throws Exception {
        String pattern = "mayur";
        Mockito.when(userServiceI.searchUser(pattern)).thenReturn(Arrays.asList(userDto, userDto));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/searchuser/" + pattern))
                .andDo(print()).andExpect(status().isOk());
    }
    @Test
    public void deleteuserTest() throws Exception {
        String userid = "rahul@123";
        Mockito.doNothing().when(userServiceI).deleteUser(userid);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/user/" + userid))
                .andDo(print()).andExpect(status().isOk());
        Mockito.verify(userServiceI,Mockito.times(1)).deleteUser(userid);
    }
}

