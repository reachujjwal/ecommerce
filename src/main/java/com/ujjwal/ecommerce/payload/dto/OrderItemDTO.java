package com.ujjwal.ecommerce.payload.dto;


import com.ujjwal.ecommerce.model.*;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private Long id;

    private Integer quantity;

    private Double price;

    private Long productId;

    private ProductDTO product;

    private Long orderId;
}
