package com.ujjwal.ecommerce.service;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.model.ShiftReport;
import com.ujjwal.ecommerce.payload.dto.ShiftReportDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShiftReportService {

    ShiftReportDTO startShift() throws Exception;

    ShiftReportDTO endShift(Long shiftReportId,
                            LocalDateTime shiftEnd) throws Exception;

    ShiftReportDTO getShiftReportById(Long id) throws Exception;

    List<ShiftReportDTO> getAllShiftReport();

    List<ShiftReportDTO> getShiftReportByBranchId(Long branchId) throws Exception;

    List<ShiftReportDTO> getShiftReportByCashierId(Long cashierId) throws Exception;

    ShiftReportDTO getCurrentShiftProgress(Long cashierId) throws Exception;

    ShiftReportDTO getShiftByCashierAndDate(Long cashierId , LocalDateTime date) throws Exception;

}
