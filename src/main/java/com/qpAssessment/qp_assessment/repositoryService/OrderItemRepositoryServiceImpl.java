package com.qpAssessment.qp_assessment.repositoryService;

import com.qpAssessment.qp_assessment.model.OrderItem;
import com.qpAssessment.qp_assessment.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemRepositoryServiceImpl implements OrderItemRepositoryService{

    private OrderItemRepository orderItemRepository;

    public OrderItemRepositoryServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
