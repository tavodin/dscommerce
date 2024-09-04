package com.tavodin.dscommerce.repositories;

import com.tavodin.dscommerce.entities.OrderItem;
import com.tavodin.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
