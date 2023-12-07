package com.example.springtest.service;

import com.example.springtest.dto.branch.*;
import com.example.springtest.exceptions.controller.BranchAlreadyExistsException;
import com.example.springtest.model.Branch;
import com.example.springtest.model.Order;
import com.example.springtest.model.Warehouse;
import com.example.springtest.model.types.OrderState;
import com.example.springtest.repository.BranchRepository;
import com.example.springtest.repository.EmployeeRepository;
import com.example.springtest.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeRepository employeeRepository;

    final static DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

    @Transactional
    public List<Branch> getAllBranches() {
        return branchRepository.getAllBranches();
    }

    @Transactional
    public List<Branch> getAllBranches(GetAllRequest request) {

        List<Branch> branches = branchRepository.getAllBranches(request.getAddress(), request.getWarehouse(), request.getDirector(), request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));

        branches.removeIf((branch) -> {
            if (request.getWarehouse().isEmpty()) {
                return false;
            }

            if (branch.getWarehouse() != null) {
                String address = branch.getWarehouse().getAddress();

                return !address.contains(request.getWarehouse());
            }

            return true;
        });

        return branches;
    }

    @Transactional
    public long getTotalCount(GetTotalBranchesCountRequest request) {

        List<Branch> branches = branchRepository.getTotalCount(request.getAddress(), request.getWarehouse(), request.getDirector());

        branches.removeIf((branch) -> {
            if (request.getWarehouse().isEmpty()) {
                return false;
            }

            if (branch.getWarehouse() != null) {
                String address = branch.getWarehouse().getAddress();

                return !address.contains(request.getWarehouse());
            }

            return true;
        });

        int count = branches.size();

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

        ZonedDateTime creationDateTime = ZonedDateTime.now();

        Branch createdBranch = branchRepository.createBranch(UUID.randomUUID(), request.getAddress(), request.getDirectorName(), request.getAdminName(), request.getSchedule(), creationDateTime, creationDateTime);

        Optional<Warehouse> warehouse = warehouseRepository.findByAddress(request.getWarehouseAddress());

        if (warehouse.isPresent()) {
            createdBranch.setWarehouse(warehouse.get());

            branchRepository.connectBranchWithWarehouse(createdBranch.getId(), warehouse.get().getId());
        }

        return createdBranch;
    }

    @Transactional
    public List<Branch> findBranchesWithoutWarehouse() {
        return branchRepository.findBranchesWithoutWarehouse();
    }

    @Transactional
    public CalculateBranchesProfitResponse calculateProfit(CalculateBranchesProfitRequest request) {

        ZonedDateTime start = ZonedDateTime.parse(request.getStartDate(), formatter);

        ZonedDateTime end = ZonedDateTime.parse(request.getEndDate(), formatter).plusDays(1).minusSeconds(1);

        List<CalculateBranchesProfitResponse.Data> dataList = new ArrayList<>();

        List<Branch> branchList = branchRepository.getAllBranches(request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));

        for (Branch branch : branchList) {
            float income = (float) branch.getOrders().stream().filter((order ->
                    (order.getCreationDate().equals(start) || order.getCreationDate().isAfter(start)) &&
                            (order.getCreationDate().equals(end) || order.getCreationDate().isBefore(end))
            )).mapToDouble(Order::getPrice).sum();

            float spending = (float) warehouseRepository.findById(branch.getWarehouse().getId()).get().getRemovedProducts().stream()
                    .filter(removed ->
                            (removed.getCreationDate().equals(start) || removed.getCreationDate().isAfter(start)) &&
                                    (removed.getCreationDate().equals(end) || removed.getCreationDate().isBefore(end))
                    )
                    .mapToDouble(removed -> removed.getAmount() * removed.getProduct().getPrice())
                    .sum();

            dataList.add(CalculateBranchesProfitResponse.Data.builder()
                    .branchId(branch.getId().toString())
                    .branchAddress(branch.getAddress())
                    .income(income)
                    .spending(spending)
                    .profit(income - spending)
                    .build());
        }

        return new CalculateBranchesProfitResponse(dataList);
    }

    @Transactional
    public CalculateBranchesLoadResponse calculateLoad(CalculateBranchesLoadRequest request) {
        ZonedDateTime start = ZonedDateTime.parse(request.getStartDate(), formatter);

        ZonedDateTime end = ZonedDateTime.parse(request.getEndDate(), formatter).plusDays(1).minusSeconds(1);

        List<CalculateBranchesLoadResponse.Data> dataList = new ArrayList<>();

        List<Branch> branchList = branchRepository.getAllBranches(request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));

        for (Branch branch : branchList) {
            int servicesCompleted = branch.getOrders().stream()
                    .filter((order ->
                            (order.getCreationDate().equals(start) || order.getCreationDate().isAfter(start)) &&
                                    (order.getCreationDate().equals(end) || order.getCreationDate().isBefore(end))
                    ))
                    .filter(order -> order.getState().equals(OrderState.COMPLETED))
                    .mapToInt(order -> order.getServices().size()).sum();

            float spentProductsCoefficient = (float) warehouseRepository.findById(branch.getWarehouse().getId()).get().getRemovedProducts().stream()
                    .filter(removed ->
                            (removed.getCreationDate().equals(start) || removed.getCreationDate().isAfter(start)) &&
                                    (removed.getCreationDate().equals(end) || removed.getCreationDate().isBefore(end))
                    )
                    .mapToDouble(removed -> removed.getAmount() * removed.getProduct().getPrice() / 100)
                    .sum();

            dataList.add(CalculateBranchesLoadResponse.Data.builder()
                    .branchId(branch.getId().toString())
                    .branchAddress(branch.getAddress())
                    .spentProductsCoefficient(spentProductsCoefficient)
                    .servicesCompleted(servicesCompleted)
                    .build());
        }

        return new CalculateBranchesLoadResponse(dataList);
    }
}
