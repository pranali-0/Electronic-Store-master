package com.icwd.electronic.store.service.Impl;

import com.icwd.electronic.store.dto.CreateOrderRequest;
import com.icwd.electronic.store.dto.OrderDto;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.entity.*;
import com.icwd.electronic.store.exception.BadApiRequest;
import com.icwd.electronic.store.exception.ResourceNotFoundException;
import com.icwd.electronic.store.service.OrderServiceI;
import com.icwd.electronic.store.repository.userRepository;
import com.icwd.electronic.store.repository.OrderRepo;
import com.icwd.electronic.store.repository.cartRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl implements OrderServiceI {


    @Autowired
    private userRepository userRepository;

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private cartRepo cartRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest request) {
        String id = request.getId();
        String cartId = request.getCartId();
        //fetch user
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        //fetch cart
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart with given id not found"));
        List<CartItem> cartItems=cart.getItems();

        if(cartItems.size() <= 0){
            throw new BadApiRequest("Invalid number of items !!");
        }

        Order order = Order.builder()
                .billingName(request.getBillingName())
                .billingPhone(request.getBillingPhone())
                .billingAddress(request.getBillingAddress())
                .orderDate(new Date())
                .paymentStatus(request.getPaymentStatus())
                .orderStatus(request.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();


        AtomicReference<Double> orderAmount=new AtomicReference<>(0.0);
        //
        List<OrderItem> orderItems= cartItems.stream().map(cartItem -> {
            //cartitem=> orderItem
            OrderItem orderItem=OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getTotalPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepo.save(cart);
        Order orderSaved = orderRepo.save(order);

        return modelMapper.map(orderSaved,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String id) {
        return null;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(String id,int pageNumber, int pageSize, String sortBy, String sortDir) {
        return new PageableResponse<>();
    }
}
