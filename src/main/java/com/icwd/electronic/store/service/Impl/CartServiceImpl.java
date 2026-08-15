package com.icwd.electronic.store.service.Impl;

import com.icwd.electronic.store.constants.AppConstants;
import com.icwd.electronic.store.dto.AddItemsToCart;
import com.icwd.electronic.store.dto.CartDto;
import com.icwd.electronic.store.entity.Cart;
import com.icwd.electronic.store.entity.CartItem;
import com.icwd.electronic.store.entity.Product;
import com.icwd.electronic.store.entity.User;
import com.icwd.electronic.store.exception.BadApiRequest;
import com.icwd.electronic.store.exception.ResourceNotFoundException;
import com.icwd.electronic.store.repository.CartItemRepo;
import com.icwd.electronic.store.repository.ProductRepo;
import com.icwd.electronic.store.repository.cartRepo;
import com.icwd.electronic.store.service.CartServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.icwd.electronic.store.repository.userRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartServiceI {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private userRepository userRepo;

    @Autowired
    private cartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartItemRepo cartIemRepo;



    @Override
    public CartDto addItemToCart(String id, AddItemsToCart request) {

        log.info("Initiating dao request for adding items into cart  with user id {}:",id);
        Double quantity = request.getQuantity();

        String productId = String.valueOf(request.getProductId());

        if (quantity <= 0) {
            throw new BadApiRequest(AppConstants.INVALID_QUANTITY);
        }
        //fetch product
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        //fetch user
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart=null;
        try {
            cart = cartRepo.findByUser(user).get();
        } catch (NoSuchElementException e) {
        cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }


        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        // perform cart operation
        List<CartItem> items=cart.getItems();
        List<CartItem> updatedITems =items.stream().map(item ->{

            if(item.getProduct().getProductId().equals(productId)){
                //item alreadypresent
                item.setQuantity(quantity + item.getQuantity()); // change
                item.setTotalPrice(quantity * product.getPrice() + item.getTotalPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedITems);

        //create item
        if(!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDescountprice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updateCart = cartRepo.save(cart);
        log.info("Completed dao call to add item into cart with userId:{}", id);
        return modelMapper.map(updateCart, CartDto.class);
    }



    @Override
    public void removeItemFromCart(String id, int cartItem) {
        log.info("Initiating dao request for removing items from cart with user id {}:",id);
        CartItem cart1=cartIemRepo.findById(cartItem).orElseThrow(()->new ResourceNotFoundException(AppConstants.ITEM_NOT_FOUND));
        log.info("Completed dao call to remove item from cart with userId:{}", id);
        cartIemRepo.delete(cart1);
    }

    @Override
    public void clearCart(String id) {
        log.info("Initiating dao request for clear items from cart with user id {}:",id);
        User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        Cart cart = cartRepo.findByUser(user).orElseThrow(()-> new ResourceNotFoundException(AppConstants.ITEM_NOT_FOUND));

        cart.getItems().clear();
        cartRepo.save(cart);
        log.info("Completed dao call to clear item from cart with userId:{}", id);

    }

    @Override
    public CartDto getCartByUser(String id) {
        log.info("Initiating dao request for get cart by user items from cart with user id {}:",id);
        User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        Cart cart = cartRepo.findByUser(user).orElseThrow(()-> new ResourceNotFoundException(AppConstants.ITEM_NOT_FOUND));

        log.info("Completed dao call to get cartby user item from cart with userId:{}", id);
        return modelMapper.map(cart,CartDto.class);
    }
}
