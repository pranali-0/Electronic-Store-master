package com.icwd.electronic.store.service;

import com.icwd.electronic.store.dto.AddItemsToCart;
import com.icwd.electronic.store.dto.CartDto;
import org.springframework.stereotype.Service;

@Service
public interface CartServiceI {



    CartDto addItemToCart(String id, AddItemsToCart request);


    void removeItemFromCart(String id,int cartItem);


    void clearCart(String id);

    CartDto getCartByUser(String id);
}
