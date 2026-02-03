package com.ujjwal.ecommerce.repository;

import com.ujjwal.ecommerce.model.OrderItem;
import com.ujjwal.ecommerce.payload.dto.OrderItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
