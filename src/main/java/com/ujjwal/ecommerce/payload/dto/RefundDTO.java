package com.ujjwal.ecommerce.payload.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ujjwal.ecommerce.domain.PaymentType;
import com.ujjwal.ecommerce.model.Branch;
import com.ujjwal.ecommerce.model.Order;
import com.ujjwal.ecommerce.model.ShiftReport;
import com.ujjwal.ecommerce.model.User;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RefundDTO {

    private Long id;

    private OrderDTO order;
    private Long orderId;

    private String reason;

    private Double amount;

    //private ShiftReport shiftReport;
    private Long shiftReportId;

    private UserDto cashier;
    private String cashierName;

    private BranchDTO branch;
    private Long branchId;

    private PaymentType paymentType;

    private LocalDateTime createdAt;

}
