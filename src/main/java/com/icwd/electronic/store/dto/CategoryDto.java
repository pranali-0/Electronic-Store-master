package com.icwd.electronic.store.dto;

import com.icwd.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {


    private String categoryId;
    @Size(min = 3 , max = 20, message = "The Title field is required.")
    private String title;
    @NotBlank(message = "The Description field is not Blank.")
    @Size(min = 10 , max = 200)
    private String description;
    @ImageNameValid
    private String coverImage;

}
