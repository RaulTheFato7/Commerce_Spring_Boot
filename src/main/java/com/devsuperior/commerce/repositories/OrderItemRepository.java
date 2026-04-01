package com.devsuperior.commerce.repositories;

import com.devsuperior.commerce.entities.OrderItem;
import com.devsuperior.commerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
