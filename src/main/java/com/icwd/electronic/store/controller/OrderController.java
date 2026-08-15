package com.icwd.electronic.store.controller;

import com.icwd.electronic.store.dto.ApiResponse;
import com.icwd.electronic.store.dto.CategoryDto;
import com.icwd.electronic.store.dto.CreateOrderRequest;
import com.icwd.electronic.store.dto.OrderDto;
import com.icwd.electronic.store.dto.PageableResponse;
import com.icwd.electronic.store.service.FileService;
import com.icwd.electronic.store.service.OrderServiceI;
import com.icwd.electronic.store.service.categoryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class OrderController {

    @Autowired
    private OrderServiceI orderService;

    /**
     * @author NikhPhalke
     * @param request
     * @param
     * @return
     * @apiNote Create A Category
     * @since 1.0v
     */


    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Initiating dao request for creating order");
        OrderDto order = orderService.createOrder(request);
        log.info("Completed dao call to create order");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * @author Nikhil Phalke
     * @apiNote api fir Remove order with orderId
     * @param orderId
     * @return
     * @since 1.0v
     */


    @DeleteMapping("/remove/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        log.info("Initiating dao request for removing order with orderId {}:",orderId);
        orderService.removeOrder(orderId);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Order is removed !!")
                .success(true)
                .build();
        log.info("Completed dao call to removing order with orderId {}:",orderId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


    /**
     * @author Nikhil Phalke
     * @apiNote get all order of user with userId
     * @param id
     * @return
     * @since 1.0v
     */

    //get orders of user
    @GetMapping("/user/{id}")
    public  ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String id){
        log.info("Initiating dao request for getting order of user with user id {}:",id);
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(id);
        log.info("Completed dao call to getting order of user with user id {}:",id);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

    /**
     * @author Nikhil Phalke
     * @apiNote get All orders of Users
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @param  id
     * @return
     * @since 1.0v
     */

    @GetMapping("/{id}")
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @PathVariable String id,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        log.info("Initiating dao request for getting order with user id{}:",id);
        PageableResponse<OrderDto> orders = orderService.getOrders(id,pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed dao call to get order of user with userId:{}", id);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
    public class categoryController {

    @Autowired
    private categoryService service;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @param categoryDto
     * @param
     * @return
     * @author NikhPhalke
     * @apiNote Create A Category
     * @since 1.0v
     */

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCat(@RequestBody CategoryDto categoryDto){
        log.info("Entering The Request For Save  UserData");
        CategoryDto category = this.service.createCategory(categoryDto);
        log.info("Completed The Request For Save UserData");
        return  new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @param categoryDto
     * @param categoryId
     * @return
     * @author NikhilPhalke
     * @apiNote Update A Category
     * @since 1.0v
     */
    @PostMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCat(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        log.info("Entering The Request For Update UserData :{}", categoryId);
        CategoryDto categoryDto1 = this.service.updateCategory(categoryDto, categoryId);
        log.info("Completed The Request For Update UserData :{}", categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }
}
}