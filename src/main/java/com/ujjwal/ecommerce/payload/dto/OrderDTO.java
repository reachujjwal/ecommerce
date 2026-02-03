package com.ujjwal.ecommerce.payload.dto;

import com.ujjwal.ecommerce.domain.PaymentType;
import com.ujjwal.ecommerce.model.Branch;
import com.ujjwal.ecommerce.model.Customer;
import com.ujjwal.ecommerce.model.OrderItem;
import com.ujjwal.ecommerce.model.User;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private BranchDTO branch;

    private UserDto cashier;

    private Customer customer;

    private Long branchId;
    private Long customerId;

    private PaymentType paymentType;

    private List<OrderItemDTO> items;
}
