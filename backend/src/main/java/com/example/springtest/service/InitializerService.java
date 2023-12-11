package com.example.springtest.service;

import com.example.springtest.dto.branch.CreateBranchRequest;
import com.example.springtest.dto.client.CreateClientRequest;
import com.example.springtest.dto.employee.CreateEmployeeCustomRequest;
import com.example.springtest.dto.employee.CreateEmployeeRequest;
import com.example.springtest.dto.order.*;
import com.example.springtest.dto.shift.CreateShiftRequest;
import com.example.springtest.dto.warehouse.AddProductsRequest;
import com.example.springtest.dto.warehouse.CreateWarehouseRequest;
import com.example.springtest.dto.warehouse.RemoveProductsRequest;
import com.example.springtest.model.types.ProductType;
import com.example.springtest.model.types.ServiceType;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.repository.InitializerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class InitializerService {

    private final List<CreateClientRequest> clientsToCreate = List.of(
            CreateClientRequest.builder()
                    .login("Client")
                    .password("Password")
                    .name("Клиент")
                    .email("client@client.com")
                    .build()
    );
    private final List<CreateEmployeeCustomRequest> employeesToCreate = List.of(
            CreateEmployeeCustomRequest.builder()
                    .id(UUID.fromString("a50e32f6-ee97-4aeb-883d-28c9fb972585"))
                    .login("Admin")
                    .password("Password")
                    .role(UserRole.ADMIN.toString())
                    .name("Администратор")
                    .email("admin@admin.ru")
                    .phone("+73456754324356")
                    .schedule(List.of("Понедельник", "12", "15", "Вторник", "10", "19"))
                    .build(),
            CreateEmployeeCustomRequest.builder()
                    .id(UUID.fromString("9655e2a0-400a-46e9-9f46-f3e76b0ffa99"))
                    .login("Admin1")
                    .password("Password")
                    .role(UserRole.ADMIN.toString())
                    .name("Администратор1")
                    .email("admin@admin.ru")
                    .phone("+73456754324356")
                    .schedule(List.of("Понедельник", "12", "15", "Вторник", "10", "19"))
                    .build(),
            CreateEmployeeCustomRequest.builder()
                    .id(UUID.fromString("ba678c2d-e357-462e-842b-7b8a339b3a51"))
                    .login("Director")
                    .password("Password")
                    .role(UserRole.DIRECTOR.toString())
                    .name("Директор Филиала")
                    .email("director@director.ru")
                    .phone("+435682475")
                    .schedule(List.of("Понедельник", "12", "15", "Пятница", "10", "19"))
                    .build(),
            CreateEmployeeCustomRequest.builder()
                    .id(UUID.fromString("9794ecba-812f-4982-b361-fb4874187b0d"))
                    .login("Director1")
                    .password("Password")
                    .role(UserRole.DIRECTOR.toString())
                    .name("Директор Филиала1")
                    .email("director@director.ru")
                    .phone("+435682475")
                    .schedule(List.of("Понедельник", "12", "15", "Пятница", "10", "19"))
                    .build(),
            CreateEmployeeCustomRequest.builder()
                    .id(UUID.fromString("251e5763-ca1c-4178-9234-a907f76d4916"))
                    .login("Superuser")
                    .password("Password")
                    .role(UserRole.SUPERUSER.toString())
                    .name("Суперпользователь")
                    .email("super@user.us")
                    .phone("+3345765638")
                    .schedule(List.of("Понедельник", "12", "15", "Вторник", "10", "19"))
                    .build()
    );

    //    private final List<CreateSalaryRequest> salariesToCreate = List.of();

    // TODO: нужно много для расчета зп
    private final List<CreateShiftRequest> shiftsToCreate = List.of(
        CreateShiftRequest.builder()
                .employeeId("a50e32f6-ee97-4aeb-883d-28c9fb972585")
                .build()
    );

    private final Map<ServiceType, Float> servicesToCreate = Map.of(
            ServiceType.WASHING, 200f,
            ServiceType.DRYING, 300f,
            ServiceType.IRONING, 200f,
            ServiceType.POWDER, 50f,
            ServiceType.BLEACH, 100f
    );

    private final List<CreateBranchRequest> branchesToCreate = List.of(
            CreateBranchRequest.builder()
                    .address("Address")
                    .directorName("Директор Филиала")
                    .adminName("Администратор")
                    .warehouseAddress("")
                    .schedule(List.of("Понедельник", "13:00", "19:00"))
                    .build(),
            CreateBranchRequest.builder()
                    .address("Address1")
                    .directorName("Директор Филиала1")
                    .adminName("Администратор1")
                    .warehouseAddress("")
                    .schedule(List.of("Понедельник", "13:00", "19:00"))
                    .build()
    );

    private final List<CreateOrderRequest> ordersToCreate = List.of(
            CreateOrderRequest.builder()
                    .branch("Address")
                    .clientName("Клиент")
                    .services(List.of(
                            CreateOrderRequest.Service.builder()
                                    .type(ServiceType.BLEACH.toString())
                                    .count(1).build()
                    ))
                    .build(),
            CreateOrderRequest.builder()
                    .branch("Address")
                    .clientName("Клиент")
                    .services(List.of(
                            CreateOrderRequest.Service.builder()
                                    .type(ServiceType.BLEACH.toString())
                                    .count(1).build()
                    ))
                    .build(),
            CreateOrderRequest.builder()
                    .branch("Address")
                    .clientName("Клиент")
                    .services(List.of(
                            CreateOrderRequest.Service.builder()
                                    .type(ServiceType.BLEACH.toString())
                                    .count(1).build()
                    ))
                    .build(),
            CreateOrderRequest.builder()
                    .branch("Address")
                    .clientName("Клиент")
                    .services(List.of(
                            CreateOrderRequest.Service.builder()
                                    .type(ServiceType.BLEACH.toString())
                                    .count(1).build()
                    ))
                    .build(),
            CreateOrderRequest.builder()
                    .branch("Address")
                    .clientName("Клиент")
                    .services(List.of(
                            CreateOrderRequest.Service.builder()
                                    .type(ServiceType.BLEACH.toString())
                                    .count(1).build()
                    ))
                    .build(),
            CreateOrderRequest.builder()
                    .branch("Address")
                    .clientName("Клиент")
                    .services(List.of(
                            CreateOrderRequest.Service.builder()
                                    .type(ServiceType.BLEACH.toString())
                                    .count(1).build()
                    ))
                    .build()
    );

    // TODO: to change state we somehow need to know ids... Create custom CreateOrderRequest?
    private final List<ApproveOrderRequest> ordersToApprove = List.of();
    private final List<GetReadyOrderRequest> ordersToGetReady = List.of();
    private final List<CompleteOrderRequest> ordersToComplete = List.of();
    private final List<CancelOrderRequest> ordersToCancel = List.of();

    private final Map<ProductType, Float> productsToCreate = Map.of(
            ProductType.WASHING_POWDER, 100f,
            ProductType.BLEACH, 300f,
            ProductType.WASHING_MACHINE, 10000f,
            ProductType.DRYER, 10000f,
            ProductType.IRONING_BOARD, 1300f,
            ProductType.IRON, 2500f
    );

    private final List<CreateWarehouseRequest> warehousesToCreate = List.of(
            CreateWarehouseRequest.builder()
                    .address("Warehouse Address")
                    .branchAddress("Address")
                    .schedule(List.of("Понедельник", "13:00", "19:00"))
                    .build(),
            CreateWarehouseRequest.builder()
                    .address("Warehouse Address1")
                    .branchAddress("Address1")
                    .schedule(List.of("Понедельник", "13:00", "19:00"))
                    .build()
    );

    // TODO: нужно много для расчета прибыли и нагрузки филиалов
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

        for (CreateEmployeeCustomRequest request : employeesToCreate) {
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


        for (CreateBranchRequest request : branchesToCreate) {
            try {
                branchService.createBranch(request);
            } catch (Exception ignored) {
            }
        }

        for (CreateOrderRequest request : ordersToCreate) {
            try {
                orderService.createOrder(request);
            } catch (Exception ex) {
                ex.printStackTrace();
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
