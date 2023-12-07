package com.example.springtest.controller;


import com.example.springtest.dto.employee.GetDirectorsWithoutBranchResponse;
import com.example.springtest.dto.warehouse.*;
import com.example.springtest.model.Warehouse;
import com.example.springtest.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("api/warehouse/get_warehouses_no_branch")
    public GetDirectorsWithoutBranchResponse getWarehousesWithoutBranch() {

        List<Warehouse> warehouses = warehouseService.findWarehousesWithoutBranch();

        return GetDirectorsWithoutBranchResponse.builder().names(
                warehouses.stream().map(Warehouse::getAddress).toList()
        ).build();

    }

    @GetMapping("api/warehouse/get")
    public GetWarehouseResponse getWarehousesWithoutBranch(@RequestParam("id") String warehouseId) {

        Warehouse warehouse = warehouseService.findWarehouseById(warehouseId);

        return GetWarehouseResponse.builder()
                .address(warehouse.getAddress())
                .branchId(warehouse.getBranch().getId().toString())
                .branchAddress(warehouse.getBranch().getAddress())
                .schedule(warehouse.getSchedule())
                .products(warehouse.getProducts().stream().map((
                        store -> GetWarehouseResponse.ProductData.builder()
                                .type(store.getProduct().getType().toString())
                                .amount(store.getAmount())
                                .build()
                )).toList())
                .creationDate(warehouse.getCreationDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .editDate(warehouse.getEditDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build();

    }

    @PostMapping("/api/warehouse/create")
    public void createBranch(@RequestBody CreateWarehouseRequest request) {
        warehouseService.createWarehouse(request);
    }

    @GetMapping("/api/warehouse/all")
    public GetAllResponse getAllWarehouses(
            @RequestParam("address") String address,
            @RequestParam("branch") String branch,
            @RequestParam("elementsOnPage") int elementsOnPage,
            @RequestParam("page") int page
    ) {
        List<Warehouse> warehousesList = warehouseService.getAllWarehouses(GetAllRequest.builder()
                .address(address)
                .branch(branch)
                .elementsOnPage(elementsOnPage)
                .page(page)
                .build());

        List<GetAllResponse.Data> data = warehousesList.stream()
                .map(warehouse -> GetAllResponse.Data.builder()
                        .id(warehouse.getId().toString())
                        .address(warehouse.getAddress())
                        .branch(warehouse.getBranch().getAddress())
                        .build()
                ).toList();

        return new GetAllResponse(data);
    }

    @GetMapping("/api/warehouse/all_count")
    public long getTotalBranchesCount(
            @RequestParam("address") String address,
            @RequestParam("branch") String branch,
            @RequestParam("elementsOnPage") int elementsOnPage
    ) {

        return warehouseService.getTotalCount(GetTotalWarehousesCountRequest.builder()
                .address(address)
                .branch(branch)
                .elementsOnPage(elementsOnPage)
                .build());
    }

    @PostMapping("/api/warehouse/delete_list")
    public void deleteWarehouses(@RequestBody DeleteWarehousesRequest request) {
        warehouseService.deleteWarehouses(request.getIdList().stream().map(UUID::fromString).toList());
    }

}
