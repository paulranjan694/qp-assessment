package com.qpAssessment.qp_assessment.repository;

import com.qpAssessment.qp_assessment.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem,Long> {
}
