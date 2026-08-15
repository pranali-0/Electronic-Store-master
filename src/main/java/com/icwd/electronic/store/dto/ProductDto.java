package com.icwd.electronic.store.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {


    private String productId;
    @NotBlank
    @Size(min = 5,max=50,message = "Product Title Should be minimum 5 character & Maximum 50 character ")
    private String title;
    @NotBlank
    @Size(min = 5,max=250 ,message = "Product Title Should be minimum 5 character & Maximum 250 character ")
    private String d,escription;
    @NotNull(message = "The Price field is not blank.")
    private Double price;
    @NotNull(message = "The Quantity field is not blank.")
    private Long quantity;

    private Date addeddate;

    private Boolean live;

    private Boolean stock;

    private Double descountprice;

    private String productimagename;

    private CategoryDto categories;
}
