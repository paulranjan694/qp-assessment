package com.qpAssessment.qp_assessment.repository;

import com.qpAssessment.qp_assessment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
