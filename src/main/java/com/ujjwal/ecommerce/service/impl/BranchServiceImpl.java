package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.mapper.BranchMapper;
import com.ujjwal.ecommerce.model.Branch;
import com.ujjwal.ecommerce.model.Store;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.BranchDTO;
import com.ujjwal.ecommerce.repository.BranchRepository;
import com.ujjwal.ecommerce.repository.StoreRepository;
import com.ujjwal.ecommerce.service.BranchService;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public BranchDTO createBranch(BranchDTO branchDto) throws UserException {
        User currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId());

        Branch branch      = BranchMapper.toEntity(branchDto, store);
        Branch savedBranch =  branchRepository.save(branch);
        return BranchMapper.toDTO(savedBranch);
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                ()-> new Exception("Branch not found.!")
        );
        existing.setName(branchDTO.getName());
        existing.setEmail(branchDTO.getEmail());
        existing.setPhone(branchDTO.getPhone());
        existing.setAddress(branchDTO.getAddress());
        existing.setWorkingDays(branchDTO.getWorkingDays());
        existing.setOpenTime(branchDTO.getOpenTime());
        existing.setCloseTime(branchDTO.getCloseTime());
        existing.setUpdatedAt(LocalDateTime.now());

        Branch updateBranch = branchRepository.save(existing);

        return BranchMapper.toDTO(updateBranch);
    }

    @Override
    public void deleteBranch(Long id) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                ()-> new Exception("Branch not found.!")
        );

        branchRepository.delete(existing);
    }

    @Override
    public List<BranchDTO> findAllBranchesByStoreId(Long storeId) {
        List<Branch> branches = branchRepository.findByStoreId(storeId);
        return branches.stream()
                .map(BranchMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BranchDTO getBranchById(Long id) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                ()-> new Exception("Branch not found.!")
        );
        return BranchMapper.toDTO(existing);
    }
}
