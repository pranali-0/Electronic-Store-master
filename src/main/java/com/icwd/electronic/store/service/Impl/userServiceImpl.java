package com.icwd.electronic.store.service.Impl;

import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.dto.UserDto;
import com.icwd.electronic.store.entity.User;

import com.icwd.electronic.store.exception.ResourceNotFoundException;
import com.icwd.electronic.store.helper.Helper;
import com.icwd.electronic.store.service.userServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.icwd.electronic.store.repository.userRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class userServiceImpl implements userServiceI {

    public userServiceImpl(com.icwd.electronic.store.repository.userRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private  userRepository userRepository;
@Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Entering Dao Call For Save or Create The UserData");
        String string = UUID.randomUUID().toString();
        userDto.setUserid(string);
        User user = DtoToUser(userDto);
        User save = this.userRepository.save(user);
        UserDto userDto1 = UserToDto(save);
        log.info("Completed Dao Call For Save UserData ");
        return  userDto1;

    }

    @Override
    public UserDto updateUser(UserDto userDto, String userid) {
        log.info("Entering Dao Call For Update The UserData : {}", userid);
        User user = this.userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        user.setGender(userDto.getGender());
        user.setEmail(userDto.getEmail());
        user.setImagename(userDto.getImagename());
        user.setAbout(userDto.getAbout());
        User save = this.userRepository.save(user);
        UserDto userDto1 = this.UserToDto(save);
        log.info("Completed Dao Call For Update The UserData : {}", userid);
        return userDto1;
    }

    @Override
    public void deleteUser(String userid) {
        log.info("Entering Dao Call For Delete UserData :{}",userid);
        User user = this.userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userid));
        log.info("Entering Dao Call For Delete  UserData :{}",userid);
        this.userRepository.delete(user);


    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy, String sortDir) {
        log.info("Entering Dao Call For Get All UserData ");
       Sort sort= ( sortDir.equalsIgnoreCase("desc"))
                    ?(Sort.by(sortBy).descending())
                   :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> all = this.userRepository.findAll(pageable);
        PageableResponse<UserDto> response= Helper.getPageableResponse(all, UserDto.class);
        log.info("Completed Dao Call For Get All UserData ");
        return response;
    }

    @Override
    public UserDto getUserById(String userid) {
        log.info("Entering Dao Call For Get The UserData :{}", userid);
        User user = this.userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userid));
        log.info("Completed Dao Call For Get The UserData :{}", userid);
        UserDto userDto1 = this.UserToDto(user);
        return userDto1;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Entering Dao Call For Get The UserData :{}", email);
        User byEmail = this.userRepository.findByEmail(email);
        log.info("Completed Dao Call For Get The UserData :{}", email);
        UserDto userDto1 = this.UserToDto(byEmail);
        return userDto1;
    }

    @Override
    public List<UserDto> searchUser(String pattern) {
        log.info("Entering Dao Call For Search The UserData :{}", pattern);
        List<User> byUseridContaining = this.userRepository.findByUseridContaining(pattern);
        List<UserDto> collect = byUseridContaining.stream().map(user -> this.UserToDto(user)).collect(Collectors.toList());
        log.info("Completed Dao Call For Search The UserData :{}", pattern);
        return collect;
    }

    @Override
    public String getwelcome() {
        return null;
    }

    public User DtoToUser (UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
//        User user = User.builder()
//                .userid(userDto.getUserid())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .gender(userDto.getGender())
//                .about(userDto.getAbout())
//                .imagename(userDto.getImagename())
//                .build();
        return user;
    }

    public UserDto UserToDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
//        UserDto userDto = UserDto.builder()
//                .userid(user.getUserid())
//                .name(user.getName())
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .gender(user.getGender())
//                .about(user.getAbout())
//                .imagename(user.getImagename())
//                .build();
      return userDto;
    }

}
