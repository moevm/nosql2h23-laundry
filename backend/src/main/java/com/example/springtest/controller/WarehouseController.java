package com.example.springtest.controller;

import com.example.springtest.dto.employee.GetDirectorsWithoutBranchResponse;
import com.example.springtest.model.Warehouse;
import com.example.springtest.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
