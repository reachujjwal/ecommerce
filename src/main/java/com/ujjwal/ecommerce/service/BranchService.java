package com.ujjwal.ecommerce.service;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.model.Branch;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.BranchDTO;

import java.util.List;

public interface BranchService {

    BranchDTO createBranch(BranchDTO branchDTO) throws UserException;
    BranchDTO updateBranch(Long id, BranchDTO branchDTO) throws Exception;
    void deleteBranch(Long id) throws Exception;
    List<BranchDTO> findAllBranchesByStoreId(Long storeId);
    BranchDTO getBranchById(Long id) throws Exception;

}
