package com.ujjwal.ecommerce.service;

import com.ujjwal.ecommerce.domain.UserRole;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.EmployeeDTO;
import com.ujjwal.ecommerce.payload.dto.UserDto;

import java.util.List;

public interface EmployeeService {

    UserDto createStoreEmployee(UserDto employee,Long storeId) throws Exception;
    UserDto createBranchEmployee(UserDto employee,Long branchId) throws Exception;
    User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception;
    void deleteEmployee(Long employeeId) throws Exception;
    List<UserDto> findStoreEmployees(Long storeId, UserRole userRole) throws Exception;
    List<UserDto> findBranchEmployees(Long branchId, UserRole userRole) throws Exception;
}
