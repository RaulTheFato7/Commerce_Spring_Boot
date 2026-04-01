package com.devsuperior.commerce.service;

import com.devsuperior.commerce.dto.OrderDTO;
import com.devsuperior.commerce.dto.ProductDTO;
import com.devsuperior.commerce.entities.Order;
import com.devsuperior.commerce.entities.Product;
import com.devsuperior.commerce.repositories.OrderRepository;
import com.devsuperior.commerce.service.exceptionals.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return new OrderDTO(order);
    }
}
