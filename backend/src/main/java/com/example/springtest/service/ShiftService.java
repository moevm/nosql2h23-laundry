package com.example.springtest.service;

import com.example.springtest.dto.shift.CreateShiftRequest;
import com.example.springtest.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;

    public void createShift(CreateShiftRequest request) {

        LocalDate creationDate = LocalDate.now();

        shiftRepository.createShift(UUID.randomUUID(), request.getEmployeeId(), creationDate);
    }

}
