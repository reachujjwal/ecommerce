package com.ujjwal.ecommerce.repository;

import com.ujjwal.ecommerce.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RefundRepository extends JpaRepository<Refund,Long> {

    List<Refund> findByCashierIdAndCreatedAtBetween(Long cashier,
                                                  LocalDateTime from,
                                                  LocalDateTime end);

    List<Refund> findByCashierId(Long id);
    List<Refund> findByShiftReportId(Long id);
    List<Refund> findByBranchId(Long id);


}
