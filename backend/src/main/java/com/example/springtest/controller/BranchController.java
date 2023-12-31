package com.example.springtest.controller;

import com.example.springtest.dto.branch.*;
import com.example.springtest.dto.employee.GetDirectorsWithoutBranchResponse;
import com.example.springtest.model.Branch;
import com.example.springtest.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/api/branch/get_by_admin_id")
    public GetByAdminIdResonse getByAdminId(@RequestParam("admin_id") String adminId) {

        return branchService.findBranchByAdminId(adminId);

    }

    @GetMapping("/api/branch/get_by_director_id")
    public GetByAdminIdResonse getByDirectorId(@RequestParam("director_id") String directorId) {

        return branchService.findBranchByDirectorId(directorId);

    }

    @GetMapping("/api/branch/get_branches_without_warehouse")
    public GetDirectorsWithoutBranchResponse getWarehousesWithoutBranch() {

        List<Branch> branches = branchService.findBranchesWithoutWarehouse();

        return GetDirectorsWithoutBranchResponse.builder().names(
                branches.stream().map(Branch::getAddress).toList()
        ).build();

    }

    @PostMapping("/api/branch/calculate_profit")
    public CalculateBranchesProfitResponse calculateBranchesProfit(@RequestBody CalculateBranchesProfitRequest request) {
        return branchService.calculateProfit(request);
    }

    @PostMapping("/api/branch/calculate_load")
    public CalculateBranchesLoadResponse calculateBranchesProfit(@RequestBody CalculateBranchesLoadRequest request) {
        return branchService.calculateLoad(request);
    }

    @PostMapping("/api/branch/create")
    public void createBranch(@RequestBody CreateBranchRequest request) {
        branchService.createBranch(request);
    }

    @GetMapping("/api/branch/all")
    public GetAllResponse getAllBranches(
            @RequestParam("address") String address,
            @RequestParam("warehouse") String warehouse,
            @RequestParam("director") String director,
            @RequestParam("elementsOnPage") int elementsOnPage,
            @RequestParam("page") int page
    ) {
        List<Branch> branchList = branchService.getAllBranches(GetAllRequest.builder()
                .address(address)
                .warehouse(warehouse)
                .director(director)
                .elementsOnPage(elementsOnPage)
                .page(page)
                .build());

        List<GetAllResponse.Data> data = branchList.stream()
                .map(branch -> GetAllResponse.Data.builder()
                        .id(branch.getId().toString())
                        .address(branch.getAddress())
                        .warehouse((branch.getWarehouse() != null) ? branch.getWarehouse().getAddress() : "null")
                        .director(branch.getDirector().getFullName())
                        .build()
                ).toList();

        return new GetAllResponse(data);
    }

    @GetMapping("/api/branch/all_compact")
    public GetAllCompactResponse getAllBranches() {
        List<Branch> branchList = branchService.getAllBranches();

        return new GetAllCompactResponse(branchList.stream().map(Branch::getAddress).toList());
    }

    @GetMapping("/api/branch/all_count")
    public long getTotalBranchesCount(
            @RequestParam("address") String address,
            @RequestParam("warehouse") String warehouse,
            @RequestParam("director") String director,
            @RequestParam("elementsOnPage") int elementsOnPage
    ) {

        return branchService.getTotalCount(GetTotalBranchesCountRequest.builder()
                .address(address)
                .warehouse(warehouse)
                .director(director)
                .elementsOnPage(elementsOnPage)
                .build());
    }

    @PostMapping("/api/branch/delete_list")
    public void deleteBranches(@RequestBody DeleteBranchesRequest request) {
        branchService.deleteBranches(request.getIdList().stream().map(UUID::fromString).toList());
    }

}
