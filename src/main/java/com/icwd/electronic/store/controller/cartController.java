package com.icwd.electronic.store.controller;

import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.AddItemsToCart;
import com.icwd.electronic.store.dto.ApiResponse;
import com.icwd.electronic.store.dto.CartDto;
import com.icwd.electronic.store.service.CartServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Slf4j
public class cartController {
    @Autowired
    private CartServiceI cartService;
    /**
     * @author Nikhil Phalke
     * @apiNote api for create cart with userId
     * @param id
     * @param request
     * @return
     * @since 1.0v
     */
    @PostMapping("/{id}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String id, @RequestBody AddItemsToCart request) {
        log.info("Initiating dao request for add item to cart with user id {}:",id);
        CartDto cartDto = cartService.addItemToCart(id, request);
        log.info("Completed dao call to add item into cart with userId:{}", id);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
    /**
     * @author Nikhil Phalke
     * @apiNote api for Remove item from cart with userId And itemId
     * @param id
     * @param itemId
     * @return
     * @since 1.0v
     */


    @GetMapping("/{id}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String id, @PathVariable int itemId){
        log.info("Initiating dao request for remove item from cart with user id {}:",id);
        cartService.removeItemFromCart(id,itemId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(AppConstants.ITEM_REMOVED)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Completed dao call to remove item from cart with userId:{}", id);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    /**
     * @author Nikhil Phalke
     * @apiNote api for Clear Cart with userId
     * @param id
     * @return
     * @since 1.0v
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String id){
        log.info("Initiating dao request for clear item from cart with user id {}:",id);
        cartService.clearCart(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(AppConstants.CART_NOT_BLANK)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Completed dao call to clear item from cart with userId:{}", id);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    /**
     * @author Nikhil Phalke
     * @apiNote api for get Cart by UserId
     * @param id
     * @return
     * @since 1.0v
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String id) {
        log.info("Initiating dao request for get cart item by user from cart with user id {}:",id);
        CartDto cartDto = cartService.getCartByUser(id);
        log.info("Completed dao call to get cart item by user from cart with userId:{}", id);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}

