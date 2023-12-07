package com.example.springtest.service;

import com.example.springtest.exceptions.controller.ServiceAlreadyExistsException;
import com.example.springtest.model.types.ServiceType;
import com.example.springtest.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Transactional
    public void createService(ServiceType type, float price) {
        Optional<com.example.springtest.model.Service> serviceOptional = serviceRepository.findServiceWithType(type);

        if (serviceOptional.isPresent()) {
            throw new ServiceAlreadyExistsException();
        }

        serviceRepository.createService(UUID.randomUUID(), type, price);
    }

}
