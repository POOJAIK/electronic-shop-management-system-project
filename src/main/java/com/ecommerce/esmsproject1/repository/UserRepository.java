package com.ecommerce.esmsproject1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.esmsproject1.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Email se user dhundne ke liye
     User findByEmail(String email);
}
