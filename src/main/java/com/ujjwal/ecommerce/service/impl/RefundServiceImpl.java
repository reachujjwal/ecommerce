package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.mapper.RefundMapper;
import com.ujjwal.ecommerce.model.Branch;
import com.ujjwal.ecommerce.model.Order;
import com.ujjwal.ecommerce.model.Refund;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.RefundDTO;
import com.ujjwal.ecommerce.repository.OrderRepository;
import com.ujjwal.ecommerce.repository.RefundRepository;
import com.ujjwal.ecommerce.service.RefundService;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;

    @Override
    public RefundDTO createRefund(RefundDTO refund) throws Exception {
        User cashier = userService.getCurrentUser();

        Order order = orderRepository.findById(refund.getOrderId()).orElseThrow(
                ()-> new Exception("Order Not Found")
        );

        Branch branch = order.getBranch();

        Refund createdRefund = Refund.builder()
                .order(order)
                .branch(branch)
                .cashier(cashier)
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .createdAt(refund.getCreatedAt())
                .build();

        Refund savedRefund = refundRepository.save(createdRefund);
        return RefundMapper.toDTO(savedRefund);

    }

    @Override
    public List<RefundDTO> getAllRefunds() throws Exception {
        return refundRepository.findAll()
                        .stream()
                        .map(RefundMapper::toDTO)
                        .collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByCashier(Long cashierId) throws Exception {
       return refundRepository.findByCashierId(cashierId)
               .stream()
               .map(RefundMapper::toDTO)
               .collect(Collectors.toList());

    }

    @Override
    public List<RefundDTO> getRefundByShiftReport(Long shiftReportId) throws Exception {
        return refundRepository.findByShiftReportId(shiftReportId)
                .stream()
                .map(RefundMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate, LocalDateTime endDate) throws Exception {

        return refundRepository.findByCashierIdAndCreatedAtBetween(cashierId,startDate,endDate)
                .stream()
                .map(RefundMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<RefundDTO> getRefundByBranch(Long branchId) throws Exception {
        return refundRepository.findByBranchId(branchId)
                .stream()
                .map(RefundMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RefundDTO getRefundById(Long refundId) throws Exception {
        return refundRepository.findById(refundId)
                .map(RefundMapper::toDTO).orElseThrow(
                        ()-> new Exception("Refund Not Found")
                );

    }

    @Override
    public void deleteRefund(Long refundId) throws Exception {
        this.getRefundById(refundId);
        refundRepository.deleteById(refundId);
    }
}
