package com.devsuperior.commerce.repositories;

import com.devsuperior.commerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
