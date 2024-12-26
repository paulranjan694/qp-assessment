package com.qpAssessment.qp_assessment.repositoryService;

import com.qpAssessment.qp_assessment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoryService {
    Order saveOrder(Order order);
}
