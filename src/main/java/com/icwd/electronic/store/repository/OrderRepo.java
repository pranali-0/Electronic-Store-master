package com.icwd.electronic.store.repository;

import com.icwd.electronic.store.entity.Order;
import com.icwd.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepo extends JpaRepository<Order,String> {

    List<Order> findByUser(User user);
}
