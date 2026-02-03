package com.ujjwal.ecommerce.mapper;


import com.ujjwal.ecommerce.model.Branch;
import com.ujjwal.ecommerce.model.Store;
import com.ujjwal.ecommerce.payload.dto.BranchDTO;

public class BranchMapper {

    public static BranchDTO toDTO(Branch branch) {
        return BranchDTO.builder()
                .id(branch.getId())
                .name(branch.getName())
                .email(branch.getEmail())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .closeTime(branch.getCloseTime())
                .openTime(branch.getOpenTime())
                .workingDays(branch.getWorkingDays())
                .storeId(branch.getStore()!=null?branch.getStore().getId():null)
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }

    public static Branch toEntity(BranchDTO branchDto, Store store) {
        return Branch.builder()
                .name(branchDto.getName())
                .email(branchDto.getEmail())
                .phone(branchDto.getPhone())
                .address(branchDto.getAddress())
                .store(store)
                .openTime(branchDto.getOpenTime())
                .closeTime(branchDto.getCloseTime())
                .workingDays(branchDto.getWorkingDays())
                .createdAt(branchDto.getCreatedAt())
                .updatedAt(branchDto.getUpdatedAt())
                .build();

    }
}
