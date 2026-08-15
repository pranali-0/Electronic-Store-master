package com.icwd.electronic.store.repository;

import com.icwd.electronic.store.entity.Cart;
import com.icwd.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface cartRepo extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user) ;


}
