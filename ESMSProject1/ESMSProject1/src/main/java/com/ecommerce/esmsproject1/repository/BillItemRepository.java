package com.ecommerce.esmsproject1.repository;

import com.ecommerce.esmsproject1.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {
}
