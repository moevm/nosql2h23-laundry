package com.example.springtest.service;

import com.example.springtest.dto.warehouse.CreateWarehouseRequest;
import com.example.springtest.dto.warehouse.GetAllRequest;
import com.example.springtest.dto.warehouse.GetTotalWarehousesCountRequest;
import com.example.springtest.exceptions.controller.BranchAlreadyExistsException;
import com.example.springtest.exceptions.controller.WarehouseAlreadyExistsException;
import com.example.springtest.model.Branch;
import com.example.springtest.model.Warehouse;
import com.example.springtest.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository repository;

    @Transactional
    public List<Warehouse> findWarehousesWithoutBranch() {
        return repository.findWarehousesWithoutBranch();
    }

    @Transactional
    public List<Warehouse> getAllWarehouses(GetAllRequest request) {
        return repository.getAllWarehouses(request.getAddress(), request.getBranch(), request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));
    }

    @Transactional
    public long getTotalCount(GetTotalWarehousesCountRequest request) {
        int count = repository.getTotalCount(request.getAddress(), request.getBranch(), request.getElementsOnPage());

        return (int) (Math.ceil(count / (double) request.getElementsOnPage()));
    }

    @Transactional
    public void deleteWarehouses(List<UUID> idList) {
        repository.deleteWarehouses(idList);
    }

    public void createWarehouse(CreateWarehouseRequest request) {
        Optional<Warehouse> warehouseOptional = repository.findByAddress(request.getAddress());

        if (warehouseOptional.isPresent()) {
            throw new WarehouseAlreadyExistsException();
        }

        LocalDateTime localDateTime = LocalDateTime.now();

        repository.createWarehouse(UUID.randomUUID(), request.getAddress(), request.getBranchAddress(), request.getSchedule(), localDateTime, localDateTime);
    }
}
