package com.qpAssessment.qp_assessment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroceryItemRequest {
    private String itemName;
    private double price;
    private int quantity;
}
