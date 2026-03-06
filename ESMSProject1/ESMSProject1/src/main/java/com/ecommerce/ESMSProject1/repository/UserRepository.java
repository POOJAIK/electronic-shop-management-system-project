package com.ecommerce.ESMSProject1.repository;

import com.ecommerce.ESMSProject1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
