package com.ujjwal.ecommerce.mapper;

import com.ujjwal.ecommerce.model.*;
import com.ujjwal.ecommerce.payload.dto.OrderDTO;
import com.ujjwal.ecommerce.payload.dto.ProductDTO;
import com.ujjwal.ecommerce.payload.dto.RefundDTO;
import com.ujjwal.ecommerce.payload.dto.ShiftReportDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftReportMapper {

    public static ShiftReportDTO toDTO(ShiftReport entity) {
        return ShiftReportDTO.builder()
                .id(entity.getId())
                .shiftStart(entity.getShiftStart())
                .shiftEnd(entity.getShiftEnd())
                .totalSales(entity.getTotalSales())
                .totalRefunds(entity.getTotalRefunds())
                .netSale(entity.getNetSale())
                .totalOrders(entity.getTotalOrders())
                .cashier(UserMapper.toDTO(entity.getCashier()))
                .cashierId(entity.getCashier().getId())
                .branch(BranchMapper.toDTO(entity.getBranch()))
                .branchId(entity.getBranch().getId())
                .recentOrders(mapOrders(entity.getRecentOrders()))
                .topSellingProducts(mapProducts(entity.getTopSellingProducts()))
                .refund(mapRefunds(entity.getRefund()))
                .paymentSummary(entity.getPaymentSummary())
                .build();

    }

    private static List<OrderDTO> mapOrders(List<Order> recentOrders) {
        if(recentOrders==null ||  recentOrders.isEmpty()){
            return Collections.emptyList();
        }
        return recentOrders.stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }


    private static List<ProductDTO> mapProducts(List<Product> topSellingProducts) {
        if(topSellingProducts==null || topSellingProducts.isEmpty()){
            return Collections.emptyList();
        }

        return topSellingProducts.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static List<RefundDTO> mapRefunds(List<Refund> refund) {
        if (refund == null || refund.isEmpty()) {
            return Collections.emptyList();
        }

        return refund.stream()
                .map(RefundMapper::toDTO)
                .collect(Collectors.toList());

    }


}
