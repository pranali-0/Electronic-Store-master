package com.icwd.electronic.store.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {


    private  String orderId;
    private String orderStatus="PENDING";

    private String paymentStatus="NOTPAID";
    private Double orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDat=new Date();
    private Date deliveredDate;
    private UserDto user;
    private List<OrderItemDto> orderItems=new ArrayList<>();
}
