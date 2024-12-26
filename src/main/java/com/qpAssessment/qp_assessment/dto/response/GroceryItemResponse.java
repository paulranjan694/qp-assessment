package com.qpAssessment.qp_assessment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroceryItemResponse {
    private long id;
    private String itemName;
    private double price;
    private int quantity;
    private LocalDateTime createdAt;
}
