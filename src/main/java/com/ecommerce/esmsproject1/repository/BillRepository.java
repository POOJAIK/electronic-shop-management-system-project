package com.ecommerce.esmsproject1.repository;

import com.ecommerce.esmsproject1.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
