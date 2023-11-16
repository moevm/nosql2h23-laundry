package com.example.springtest.service.impl;

import com.example.springtest.repository.TestRepository;
import com.example.springtest.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Override
    @Transactional
    public long testMethod(int param1, int param2) {
        return testRepository.findAll().size();
    }

}
