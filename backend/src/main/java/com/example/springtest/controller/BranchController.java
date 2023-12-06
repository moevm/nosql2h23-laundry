package com.example.springtest.controller;

import com.example.springtest.dto.branch.*;
import com.example.springtest.model.Branch;
import com.example.springtest.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

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

        System.out.println(branchList.toString());

        List<GetAllResponse.Data> data = branchList.stream()
                .map(branch -> GetAllResponse.Data.builder()
                        .id(branch.getId().toString())
                        .address(branch.getAddress())
                        .warehouse(branch.getWarehouse().getAddress())
                        .director(branch.getDirector().getFullName())
                        .build()
                ).toList();

        return new GetAllResponse(data);
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
    public void getTotalBranchesCount(@RequestBody DeleteBranchesRequest request) {
        branchService.deleteBranches(request.getIdList().stream().map(UUID::fromString).toList());
    }

}
