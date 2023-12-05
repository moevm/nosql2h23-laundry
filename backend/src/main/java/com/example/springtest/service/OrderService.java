package com.example.springtest.service;

import com.example.springtest.model.Order;
import com.example.springtest.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order addOrder(Order order) {
        return orderRepository.addOrder(order.getStatus(), order.getPrice(), order.getCreationDate(), order.getEditDate());
    }

    @Transactional
    // Метод для получения всех пользователей
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.getAllOrders();
    }
}

