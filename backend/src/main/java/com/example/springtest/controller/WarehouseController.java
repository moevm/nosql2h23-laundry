package com.example.springtest.controller;


import com.example.springtest.dto.branch.GetByAdminIdResonse;
import com.example.springtest.dto.employee.GetDirectorsWithoutBranchResponse;
import com.example.springtest.dto.warehouse.*;
import com.example.springtest.model.Warehouse;
import com.example.springtest.service.ProductService;
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

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private final WarehouseService warehouseService;
    private final ProductService productService;

    @GetMapping("/api/warehouse/get_by_director_id")
    public GetByAdminIdResonse getByDirectorId(@RequestParam("director_id") String directorId) {

        return warehouseService.findWarehouseByDirectorId(directorId);

    }

    @GetMapping("api/warehouse/get_warehouses_no_branch")
    public GetDirectorsWithoutBranchResponse getWarehousesWithoutBranch() {

        List<Warehouse> warehouses = warehouseService.findWarehousesWithoutBranch();

        return GetDirectorsWithoutBranchResponse.builder().names(
                warehouses.stream().map(Warehouse::getAddress).toList()
        ).build();

    }

    @GetMapping("api/warehouse/get")
    public GetWarehouseResponse getWarehouse(@RequestParam("id") String warehouseId) {

        Warehouse warehouse = warehouseService.findWarehouseById(warehouseId);

        return GetWarehouseResponse.builder()
                .address(warehouse.getAddress())
                .branchId(warehouse.getBranch().getId().toString())
                .branchAddress(warehouse.getBranch().getAddress())
                .schedule(warehouse.getSchedule())
                .products(warehouse.getProducts().stream().map((
                        store -> GetWarehouseResponse.ProductData.builder()
                                .type(store.getProduct().getType().toString())
                                .amount(store.getCount())
                                .build()
                )).toList())
                .creationDate(warehouse.getCreationDate().format(formatter))
                .editDate(warehouse.getEditDate().format(formatter))
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

    @PostMapping("/api/warehouse/add_product")
    public void addProducts(@RequestBody AddProductsRequest request) {
        productService.addProducts(request);
    }

    @PostMapping("/api/warehouse/remove_product")
    public void removeProducts(@RequestBody RemoveProductsRequest request) {
        productService.removeProducts(request);
    }

}
