package com.qpAssessment.qp_assessment.repositoryService;

import com.qpAssessment.qp_assessment.model.Order;
import com.qpAssessment.qp_assessment.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderRepositoryServiceImpl implements OrderRepositoryService{
    private OrderRepository orderRepository;

    public OrderRepositoryServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
