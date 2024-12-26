package com.qpAssessment.qp_assessment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private long id;
    private String itemName;
    private double price;
    private int quantity;
}
