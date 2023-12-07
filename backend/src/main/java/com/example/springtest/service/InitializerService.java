package com.example.springtest.service;

import com.example.springtest.dto.branch.CreateBranchRequest;
import com.example.springtest.dto.client.CreateClientRequest;
import com.example.springtest.dto.employee.CreateEmployeeRequest;
import com.example.springtest.dto.order.*;
import com.example.springtest.dto.shift.CreateShiftRequest;
import com.example.springtest.dto.warehouse.AddProductsRequest;
import com.example.springtest.dto.warehouse.CreateWarehouseRequest;
import com.example.springtest.dto.warehouse.RemoveProductsRequest;
import com.example.springtest.model.types.ProductType;
import com.example.springtest.model.types.ServiceType;
import com.example.springtest.repository.InitializerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class InitializerService {

    private final List<CreateClientRequest> clientsToCreate = List.of();
    private final List<CreateEmployeeRequest> employeesToCreate = List.of();

    //    private final List<CreateSalaryRequest> salariesToCreate = List.of();
    private final List<CreateShiftRequest> shiftsToCreate = List.of();

    private final Map<ServiceType, Float> servicesToCreate = Map.of();

    private final List<CreateOrderRequest> ordersToCreate = List.of();
    private final List<ApproveOrderRequest> ordersToApprove = List.of();
    private final List<GetReadyOrderRequest> ordersToGetReady = List.of();
    private final List<CompleteOrderRequest> ordersToComplete = List.of();
    private final List<CancelOrderRequest> ordersToCancel = List.of();


    private final List<CreateBranchRequest> branchesToCreate = List.of();

    private final Map<ProductType, Float> productsToCreate = Map.of();

    private final List<CreateWarehouseRequest> warehousesToCreate = List.of();

    private final List<AddProductsRequest> productsToAddToWarehouse = List.of();
    private final List<RemoveProductsRequest> productsToRemoveFromWarehouse = List.of();


    public InitializerService(
            ClientService clientService,
            EmployeeService employeeService,
            ShiftService shiftService,
            ServiceService serviceService,
            OrderService orderService,
            BranchService branchService,
            ProductService productService,
            WarehouseService warehouseService,
            InitializerRepository initializerRepository
    ) {

        if (initializerRepository.isInitialized()) {
            return;
        }

        for (CreateClientRequest request : clientsToCreate) {
            try {
                clientService.createClient(request);
            } catch (Exception ignored) {
            }
        }

        for (CreateEmployeeRequest request : employeesToCreate) {
            try {
                employeeService.createEmployee(request);
            } catch (Exception ignored) {
            }
        }

        for (CreateShiftRequest request : shiftsToCreate) {
            try {
                shiftService.createShift(request);
            } catch (Exception ignored) {
            }
        }


        for (Map.Entry<ServiceType, Float> data : servicesToCreate.entrySet()) {
            try {
                serviceService.createService(data.getKey(), data.getValue());
            } catch (Exception ignored) {
            }
        }


        for (CreateOrderRequest request : ordersToCreate) {
            try {
                orderService.createOrder(request);
            } catch (Exception ignored) {
            }
        }

        for (ApproveOrderRequest request : ordersToApprove) {
            try {
                orderService.approveOrders(request);
            } catch (Exception ignored) {
            }
        }

        for (GetReadyOrderRequest request : ordersToGetReady) {
            try {
                orderService.prepareOrders(request);
            } catch (Exception ignored) {
            }
        }

        for (CompleteOrderRequest request : ordersToComplete) {
            try {
                orderService.completeOrders(request);
            } catch (Exception ignored) {
            }
        }

        for (CancelOrderRequest request : ordersToCancel) {
            try {
                orderService.cancelOrders(request);
            } catch (Exception ignored) {
            }
        }


        for (CreateBranchRequest request : branchesToCreate) {
            try {
                branchService.createBranch(request);
            } catch (Exception ignored) {
            }
        }

        for (Map.Entry<ProductType, Float> data : productsToCreate.entrySet()) {
            try {
                productService.createProduct(data.getKey(), data.getValue());
            } catch (Exception ignored) {
            }
        }

        for (CreateWarehouseRequest request : warehousesToCreate) {
            try {
                warehouseService.createWarehouse(request);
            } catch (Exception ignored) {
            }
        }

        for (AddProductsRequest request : productsToAddToWarehouse) {
            try {
                productService.addProducts(request);
            } catch (Exception ignored) {
            }
        }

        for (RemoveProductsRequest request : productsToRemoveFromWarehouse) {
            try {
                productService.removeProducts(request);
            } catch (Exception ignored) {
            }
        }


        initializerRepository.setInitialized(UUID.randomUUID());

    }

}
