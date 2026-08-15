package com.icwd.electronic.store.repository;

import com.icwd.electronic.store.entity.Category;
import com.icwd.electronic.store.entity.Product;
import com.icwd.electronic.store.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product , String> {

    Page<Product>findByLiveTrue(Pageable pageable);
    Page<Product>findByTitleContaining(Pageable pageable, String pattern);

    Page<Product>findByCategories(Category category,Pageable pageable);

}
