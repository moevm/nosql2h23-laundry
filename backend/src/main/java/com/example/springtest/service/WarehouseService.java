package com.example.springtest.service;

import com.example.springtest.model.Warehouse;
import com.example.springtest.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository repository;

    @Transactional
    public List<Warehouse> findWarehousesWithoutBranch() {
        return repository.findWarehousesWithoutBranch();
    }

}
