package com.icwd.electronic.store.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_info")
public class Product {

    @Id
    private String productId;
    @Column(name="Product_Title")
    private String title;
    @Column(name="Product_Description")
    private String description;
    @Column(name="Product_Price")
    private Double price;
    @Column(name="Product_Quantity")
    private Long quantity;
    @Column(name="Product_Added_Date")
    private Date addeddate;
    @Column(name="Product_Live")
    private Boolean live;
    @Column(name="Product_Stock")
    private Boolean stock;
    @Column(name="Product_Descount_Price")
    private Double descountprice;
    @Column(name="Product_Image_Name")
    private String productimagename;
    @ManyToOne
    private Category categories;



}
