package com.ujjwal.ecommerce.mapper;

import com.ujjwal.ecommerce.model.Refund;
import com.ujjwal.ecommerce.payload.dto.RefundDTO;

public class RefundMapper {

    public static RefundDTO toDTO(Refund refund) {
        return RefundDTO.builder()
                .id(refund.getId())
                .orderId(refund.getOrder().getId())
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .cashierName(refund.getCashier().getFullName())
                .branchId(refund.getBranch().getId())
                .shiftReportId(refund.getShiftReport()!=null ? refund.getShiftReport().getId() : null)
                .createdAt(refund.getCreatedAt())
                .build();
    }
}
