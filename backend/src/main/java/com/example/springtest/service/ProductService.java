package com.example.springtest.service;

import com.example.springtest.dto.warehouse.AddProductsRequest;
import com.example.springtest.dto.warehouse.RemoveProductsRequest;
import com.example.springtest.exceptions.controller.NoSuchWarehouseException;
import com.example.springtest.model.Product;
import com.example.springtest.model.Warehouse;
import com.example.springtest.model.relationships.Store;
import com.example.springtest.model.types.ProductType;
import com.example.springtest.repository.ProductRepository;
import com.example.springtest.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public void createProduct(ProductType type, float price) {
        productRepository.createProduct(UUID.randomUUID(), type, price);
    }

    @Transactional
    public void addProducts(AddProductsRequest request) {
        ZonedDateTime editDateTime = ZonedDateTime.now();

        Warehouse warehouse = warehouseRepository.findWarehouseById(UUID.fromString(request.getWarehouse())).orElseThrow(NoSuchWarehouseException::new);

        for (AddProductsRequest.Data product : request.getProducts()) {
            if (warehouse.getProducts().stream()
                    .map(Store::getProduct)
                    .map(Product::getType)
                    .toList()
                    .contains(ProductType.valueOf(product.getName()))) {
                productRepository.addProduct(warehouse.getId(), product.getName(), product.getCount(), editDateTime);
            } else {
                productRepository.addNewProduct(warehouse.getId(), product.getName(), product.getCount(), editDateTime);
            }
        }

    }

    @Transactional
    public void removeProducts(RemoveProductsRequest request) {
        ZonedDateTime editDateTime = ZonedDateTime.now();

        Warehouse warehouse = warehouseRepository.findWarehouseById(UUID.fromString(request.getWarehouse())).orElseThrow(NoSuchWarehouseException::new);

        Map<ProductType, Integer> productList = warehouse.getProducts().stream()
                .collect(Collectors.toMap((store -> store.getProduct().getType()), Store::getCount));

        ZonedDateTime creationDate = ZonedDateTime.now();

        for (RemoveProductsRequest.Data product : request.getProducts()) {
            if (productList.get(ProductType.valueOf(product.getName())) == product.getCount()) {
                productRepository.removeWholeProduct(warehouse.getId(), product.getName(), editDateTime);
            } else {
                productRepository.removePartProduct(warehouse.getId(), product.getName(), product.getCount(), editDateTime);
            }

            productRepository.saveRemovedHistory(warehouse.getId(), product.getName(), product.getCount(), creationDate, editDateTime);
        }
    }
}
