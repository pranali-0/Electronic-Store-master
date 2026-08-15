package com.icwd.electronic.store.dto;

import com.icwd.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


    private String userid;
     @Size(min = 3 , max = 20, message = "The name field is required.")
    private String name;
     @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
     @Email(message = "Please enter valid email address")
    private String email;
     @NotBlank()
     @Size(min = 5,max = 8 ,message = "The password field is required.")
    private String password;
     @Size(min = 4 , max=8)
    private String gender;
     @NotBlank(message = "The About field is not Blank.")
     @Size(min = 10 , max = 200)
    private String about;
     @ImageNameValid
    private String imagename;



}
