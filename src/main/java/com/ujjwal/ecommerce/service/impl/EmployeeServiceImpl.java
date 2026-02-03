package com.ujjwal.ecommerce.service.impl;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.ujjwal.ecommerce.domain.UserRole;
import com.ujjwal.ecommerce.mapper.UserMapper;
import com.ujjwal.ecommerce.model.Branch;
import com.ujjwal.ecommerce.model.Store;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.UserDto;
import com.ujjwal.ecommerce.repository.BranchRepository;
import com.ujjwal.ecommerce.repository.StoreRepository;
import com.ujjwal.ecommerce.repository.UserRepository;
import com.ujjwal.ecommerce.service.EmployeeService;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception {
        Store store =  storeRepository.findById(storeId).orElseThrow(
                ()-> new Exception("Store not found")
        );

        Branch branch = null;
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            if(employee.getBranchId()==null){
                throw new Exception("Branch Id is required to create branch manager.!");
            }
            branch = branchRepository.findById(employee.getBranchId()).orElseThrow(
                    ()-> new Exception("Branch not found")
            );
        }

        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));

        User savedEmployee = userRepository.save(user);

        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER && branch!=null){
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedEmployee);
    }

    @Override
    public UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception {
        Branch branch = branchRepository.findById(employee.getBranchId()).orElseThrow(
                ()-> new Exception("Branch not found")
        );
        if(employee.getRole()==UserRole.ROLE_BRANCH_CASHIER || employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            User user =  UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            User savedEmployee = userRepository.save(user);

            return UserMapper.toDTO(savedEmployee);
        }

        throw new Exception("Branch role not supported.!");

    }

    @Override
    public User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception {
        User existingEmployee = userRepository.findById(employeeId);
        if(existingEmployee==null){
            throw new Exception("Employee not exist with this id.!");
        }

        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setFullName(employeeDetails.getFullName());
        existingEmployee.setPassword(employeeDetails.getPassword());
        existingEmployee.setRole(employeeDetails.getRole());

        Branch branch = branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(
                ()-> new Exception("Branch not found")
        );
        if(branch!=null){
            existingEmployee.setBranch(branch);
        }

        return userRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User employee = userRepository.findById(employeeId);
        if(employee==null){
            throw new Exception("Employee not found.!");
        }
        userRepository.delete(employee);
    }

    @Override
    public List<UserDto> findStoreEmployees(Long storeId, UserRole role) throws Exception {
        Store store =  storeRepository.findById(storeId).orElseThrow(
                ()-> new Exception("Store not found")
        );
        return userRepository.findByStore(store)
                .stream()
                .filter(
                        user->role==null || user.getRole()==role)
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findBranchEmployees(Long branchId, UserRole role) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                () -> new Exception("Branch not found.!")
        );

         return userRepository.findByBranchId(branchId)
                .stream().filter(
                        user-> role==null || user.getRole()==role)
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());


    }
}
