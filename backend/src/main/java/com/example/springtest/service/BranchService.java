package com.example.springtest.service;

import com.example.springtest.dto.branch.CreateBranchRequest;
import com.example.springtest.dto.branch.GetAllRequest;
import com.example.springtest.dto.branch.GetTotalBranchesCountRequest;
import com.example.springtest.exceptions.controller.BranchAlreadyExistsException;
import com.example.springtest.model.Branch;
import com.example.springtest.model.Employee;
import com.example.springtest.model.Warehouse;
import com.example.springtest.repository.BranchRepository;
import com.example.springtest.repository.EmployeeRepository;
import com.example.springtest.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public List<Branch> getAllBranches(GetAllRequest request) {

        return branchRepository.getAllBranches(request.getAddress(), request.getWarehouse(), request.getDirector(), request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));
    }

    @Transactional
    public long getTotalCount(GetTotalBranchesCountRequest request) {

        long count = branchRepository.getTotalCount(request.getAddress(), request.getWarehouse(), request.getDirector());

        return (int) (Math.ceil(count / (double) request.getElementsOnPage()));
    }

    @Transactional
    public void deleteBranches(List<UUID> idList) {
        branchRepository.deleteBranches(idList);
    }

    @Transactional
    public Branch createBranch(CreateBranchRequest request) {

        Optional<Branch> branch = branchRepository.findByAddress(request.getAddress());

        if (branch.isPresent()) {
            throw new BranchAlreadyExistsException();
        }

        LocalDateTime localDateTime = LocalDateTime.now();

        Branch createdBranch = branchRepository.createBranch(UUID.randomUUID(), request.getAddress(), request.getDirectorName(), request.getAdminName(), request.getSchedule(), localDateTime, localDateTime);

        Optional<Warehouse> warehouse = warehouseRepository.findByAddress(request.getWarehouseAddress());

        if (warehouse.isPresent()) {
            createdBranch.setWarehouse(warehouse.get());

            branchRepository.connectBranchWithWarehouse(createdBranch.getId(), warehouse.get().getId());
        }

        System.out.println(createdBranch.getAdmin());

        return createdBranch;
    }
}
