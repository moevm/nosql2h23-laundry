package com.example.springtest.service;

import com.example.springtest.dto.branch.GetAllRequest;
import com.example.springtest.dto.branch.GetTotalBranchesCountRequest;
import com.example.springtest.model.Branch;
import com.example.springtest.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository repository;

    @Transactional
    public List<Branch> getAllBranches(GetAllRequest request) {
        String addressFilter = request.getAddress();

        if (Objects.equals(addressFilter, "")) {
            addressFilter = ".*";
        } else {
            addressFilter = Pattern.quote(addressFilter);
        }

        String warehouseFilter = request.getWarehouse();

        if (Objects.equals(warehouseFilter, "")) {
            warehouseFilter = ".*";
        } else {
            warehouseFilter = Pattern.quote(warehouseFilter);
        }

        String directorFilter = request.getDirector();

        if (Objects.equals(directorFilter, "")) {
            directorFilter = ".*";
        } else {
            directorFilter = Pattern.quote(directorFilter);
        }

        return repository.getAllBranches(addressFilter, warehouseFilter, directorFilter, request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));
    }

    @Transactional
    public long getTotalCount(GetTotalBranchesCountRequest request) {
        String addressFilter = request.getAddress();

        if (Objects.equals(addressFilter, "")) {
            addressFilter = ".*";
        } else {
            addressFilter = Pattern.quote(addressFilter);
        }

        String warehouseFilter = request.getWarehouse();

        if (Objects.equals(warehouseFilter, "")) {
            warehouseFilter = ".*";
        } else {
            warehouseFilter = Pattern.quote(warehouseFilter);
        }

        String directorFilter = request.getDirector();

        if (Objects.equals(directorFilter, "")) {
            directorFilter = ".*";
        } else {
            directorFilter = Pattern.quote(directorFilter);
        }

        return (int) Math.ceil(repository.getTotalCount(addressFilter, warehouseFilter, directorFilter) / (double) request.getElementsOnPage());
    }

    public void deleteBranches(List<String> idList) {
        repository.deleteBranches(idList);
    }
}
