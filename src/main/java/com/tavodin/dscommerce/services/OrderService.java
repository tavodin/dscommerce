package com.tavodin.dscommerce.services;

import com.tavodin.dscommerce.dto.OrderDTO;
import com.tavodin.dscommerce.entities.Order;
import com.tavodin.dscommerce.repositories.OrderRepository;
import com.tavodin.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderDTO(order);
    }

}
