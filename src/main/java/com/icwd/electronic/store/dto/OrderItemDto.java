package com.icwd.electronic.store.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {


    private int orederItemId;

    private Double quantity;

    private Double totalPrice;

    private ProductDto product;

    private OrderDto order;
}
