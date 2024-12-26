package com.qpAssessment.qp_assessment.dto.response;

import java.util.ArrayList;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long orderId;
    private double totalAmount;
    private List<OrderItemResponse> items = new ArrayList<>();
}
