package com.ujjwal.ecommerce.payload.dto;

import com.ujjwal.ecommerce.domain.StoreStatus;
import com.ujjwal.ecommerce.model.StoreContact;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDTO {

    private Long id;

    private String brand;

    private UserDto storeAdmin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description;

    private String storeType;

    private StoreStatus status;

    private StoreContact contact;
}
