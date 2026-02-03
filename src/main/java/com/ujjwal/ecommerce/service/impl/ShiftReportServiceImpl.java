package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.domain.PaymentType;
import com.ujjwal.ecommerce.mapper.ShiftReportMapper;
import com.ujjwal.ecommerce.model.*;
import com.ujjwal.ecommerce.payload.dto.ShiftReportDTO;
import com.ujjwal.ecommerce.repository.*;
import com.ujjwal.ecommerce.service.ShiftReportService;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final UserService userService;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public ShiftReportDTO startShift() throws Exception {

        User currentUser = userService.getCurrentUser();
        LocalDateTime shiftStart = LocalDateTime.now();
        LocalDateTime shiftOfDay = shiftStart.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay   = shiftStart.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport> existing = shiftReportRepository.findByCashierAndShiftStartBetween(currentUser, shiftOfDay, endOfDay);

        if(existing.isPresent()){
            throw new Exception("Shift already started today.!");
        }

        Branch branch = currentUser.getBranch();

        ShiftReport shiftReport = ShiftReport.builder()
                .cashier(currentUser)
                .shiftStart(shiftStart)
                .branch(branch)
                .build();

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);
        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO endShift(Long shiftReportId, LocalDateTime shiftEnd) throws Exception {
        User currentUser = userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser).orElseThrow(
                () -> new Exception("Shift not found.")
        );

        shiftReport.setShiftEnd(shiftEnd); // end the shift here
        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(),
                shiftReport.getShiftStart(),
                shiftReport.getShiftEnd()
        );

        double totalRefunds = refunds.stream()
                .mapToDouble(refund-> refund.getAmount()!=null ? refund.getAmount():0.0)
                .sum();

        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                currentUser,
                shiftReport.getShiftStart(),
                shiftReport.getShiftEnd()

        );
        System.out.println("total orders"+orders);
        double totalSales =  orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        int totalOrders = orders.size();
        double netSale = totalSales-totalRefunds;

        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSale);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummary(getPaymentSummary(orders,totalSales));
        shiftReport.setRefund(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);
        return ShiftReportMapper.toDTO(savedReport);

    }



    @Override
    public ShiftReportDTO getShiftReportById(Long id) throws Exception {
//        return ShiftReportMapper.toDTO(
//                shiftReportRepository.findById(id).orElseThrow(
//                        () -> new Exception("Shift report not found.!")
//                )
//        );

        return shiftReportRepository.findById(id)
                .map(ShiftReportMapper::toDTO)
                .orElseThrow(
                        () -> new Exception("Shift report not found.!")
                );
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReport() {

        List<ShiftReport> reports = shiftReportRepository.findAll();

        return reports.stream()
                .map(ShiftReportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getShiftReportByBranchId(Long branchId) throws Exception {

        List<ShiftReport> reports = shiftReportRepository.findByBranchId(branchId);
        return reports.stream()
                .map(ShiftReportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getShiftReportByCashierId(Long cashierId) throws Exception {
        List<ShiftReport> reports = shiftReportRepository.findByCashierId(cashierId);
        return reports.stream()
                .map(ShiftReportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ShiftReportDTO getCurrentShiftProgress(Long cashierId) throws Exception {
        User user = userService.getCurrentUser();
        ShiftReport shiftReport =  shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(user)
                .orElseThrow(
                        () -> new Exception("No Shift not found cashier.!")
                );
        LocalDateTime now = LocalDateTime.now();
        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                user,
                shiftReport.getShiftStart(),
                now
        );
        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                user.getId(),
                shiftReport.getShiftStart(),
                now
        );

        double totalRefunds = refunds.stream()
                .mapToDouble(refund-> refund.getAmount()!=null ? refund.getAmount():0.0)
                .sum();


        double totalSales =  orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        int totalOrders = orders.size();
        double netSale = totalSales-totalRefunds;

        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSale);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummary(getPaymentSummary(orders,totalSales));
        shiftReport.setRefund(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO getShiftByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception {

        User cashier = userRepository.findById(cashierId);
        if(cashier==null){
            throw new Exception("Cashier is not found.!");
        }
        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end   = date.withHour(23).withMinute(59).withSecond(59);

        ShiftReport report = shiftReportRepository.findByCashierAndShiftStartBetween(
                cashier,
                start,
                end
        ).orElseThrow(
                () -> new Exception("No Shift not found for cashier for this date.!")
        );
        return ShiftReportMapper.toDTO(report);
    }

    // Helper mehtods are defined below

    private List<Order> getRecentOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());

    }

    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product,Integer> productSalesMap = new HashMap<>();

        for(Order order : orders){
            for (OrderItem item : order.getItems()){
                Product product = item.getProduct();
                productSalesMap.put(product,productSalesMap.getOrDefault(product,0)+item.getQuantity());
            }
        }

        // entrySet() -> gives all the rows like => [(Phone, 55), (Laptop, 40), (Mouse, 12)]
        return productSalesMap.entrySet().stream()
                .sorted((a,b)->b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<PaymentSummary> getPaymentSummary(List<Order> orders, double totalSales) {

        // CASH - order1(1000) ,order2(1000) -> 2000
        // CARD - order3 -> 3000
        // UPI  - order4  -> 1500

        Map<PaymentType,List<Order>> grouped = orders.stream()
                .collect(
                        Collectors.groupingBy(order->order.getPaymentType()!=null?order.getPaymentType():PaymentType.CASH)
                );

        List<PaymentSummary> summeries =  new ArrayList<>();
        for(Map.Entry<PaymentType,List<Order>> entry : grouped.entrySet()){
            double amount = entry.getValue()
                    .stream()
                    .mapToDouble(Order::getTotalAmount)
                    .sum();

            int transaction   = entry.getValue().size();
            double percentage = (amount/totalSales)*100;

            PaymentSummary ps = new PaymentSummary();

            ps.setType(entry.getKey());
            ps.setTotalAmount(amount);
            ps.setTransactionCount(transaction);
            ps.setPercentage(percentage);

            summeries.add(ps);

        }
        return summeries;
    }




}
