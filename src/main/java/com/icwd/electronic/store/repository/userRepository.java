package com.icwd.electronic.store.repository;

import com.icwd.electronic.store.dto.UserDto;
import com.icwd.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userRepository extends JpaRepository<User, String> {

    public User findByEmail (String email);

    public List<User> findByUseridContaining (String pattern);

}
