package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.domain.OrderStatus;
import com.ujjwal.ecommerce.domain.PaymentType;
import com.ujjwal.ecommerce.mapper.OrderMapper;
import com.ujjwal.ecommerce.model.*;
import com.ujjwal.ecommerce.payload.dto.OrderDTO;
import com.ujjwal.ecommerce.repository.OrderRepository;
import com.ujjwal.ecommerce.repository.ProductRepository;
import com.ujjwal.ecommerce.service.OrderService;
import com.ujjwal.ecommerce.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;


    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) throws Exception {
        User cashier  = userService.getCurrentUser();
        Branch branch = cashier.getBranch();
        if(branch==null){
            throw new Exception("Cashier's branch is not found.!");
        }

        Order order = Order.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(orderDTO.getCustomer())
                .paymentType(orderDTO.getPaymentType())
                .build();

        List<OrderItem> orderItems = orderDTO.getItems()
                .stream()
                .map(itemDto -> {
                        Product product = productRepository.findById(itemDto.getProductId()).orElseThrow(
                                ()-> new EntityNotFoundException("Product not found.!")
                        );
                        return OrderItem.builder()
                                .product(product)
                                .quantity(itemDto.getQuantity())
                                .price(product.getSellingPrice()*itemDto.getQuantity())
                                .order(order)
                                .build();
                }).toList();


        double totalAmount = orderItems.stream()
                .mapToDouble(
                        OrderItem::getPrice
                ).sum();
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder =  orderRepository.save(order);
        return OrderMapper.toDto(savedOrder);

    }

    @Override
    public OrderDTO getOrderById(Long id) throws Exception {
        return orderRepository.findById(id)
                .map(OrderMapper::toDto)
                .orElseThrow(
                () -> new Exception("Order not found with id "+ id)
        );
    }

    @Override
    public List<OrderDTO> getOrdersByBranch(Long branchId,
                                            Long customerId,
                                            Long cashierId,
                                            PaymentType paymentType,
                                            OrderStatus status) throws Exception {
        return orderRepository.findByBranchId(branchId)
                .stream()
                .filter(order ->
                        customerId==null || (order.getCustomer()!=null && order.getCustomer().getId().equals(customerId)))
                .filter(order ->
                        cashierId==null || (order.getCashier()!=null && order.getCashier().getId().equals(cashierId)))
                .filter(order ->
                        paymentType==null || order.getPaymentType()==paymentType)
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());


    }

    @Override
    public List<OrderDTO> getOrderByCashier(Long cashierId) throws Exception {
        return orderRepository.findByCashierId(cashierId)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new Exception("Order not found with id "+id)
        );
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDTO> getTodayOrdersByBranch(Long branchId) throws Exception {
        LocalDate today     = LocalDate.now();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end   = today.plusDays(1).atStartOfDay();

        return orderRepository.findByBranchIdAndCreatedAtBetween(branchId,start,end)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) throws Exception {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) throws Exception {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }
}
