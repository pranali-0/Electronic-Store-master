package com.icwd.electronic.store.repository;

import com.icwd.electronic.store.entity.Cart;
import com.icwd.electronic.store.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem ,Integer> {
}
