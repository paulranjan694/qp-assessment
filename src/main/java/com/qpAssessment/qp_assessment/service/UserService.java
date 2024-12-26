package com.qpAssessment.qp_assessment.service;

import com.qpAssessment.qp_assessment.dto.request.OrderRequest;
import com.qpAssessment.qp_assessment.dto.response.GetAllGroceryItemsResponse;
import com.qpAssessment.qp_assessment.dto.response.OrderResponse;

public interface UserService {

    GetAllGroceryItemsResponse getItems(int pageNumber, int pageSize);

    OrderResponse createOrder(OrderRequest orderRequest);

}
