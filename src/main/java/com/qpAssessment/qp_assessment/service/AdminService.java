package com.qpAssessment.qp_assessment.service;

import com.qpAssessment.qp_assessment.dto.request.GroceryItemRequest;
import com.qpAssessment.qp_assessment.dto.request.UpdateInventoryRequest;
import com.qpAssessment.qp_assessment.dto.response.GetAllGroceryItemsResponse;
import com.qpAssessment.qp_assessment.dto.response.GroceryItemResponse;
import com.qpAssessment.qp_assessment.dto.response.UpdateInventoryResponse;

public interface AdminService {

    GroceryItemResponse addItem(GroceryItemRequest groceryItemRequest);

    GetAllGroceryItemsResponse getItems(int pageNo, int pageSize);

    GroceryItemResponse updateItem(long itemId, GroceryItemRequest groceryItemRequest);

    void deleteItem(long itemId);


    GetAllGroceryItemsResponse getItemsInventory(int pageNumber, int pageSize, int minAvailableQuantity);

    UpdateInventoryResponse updateInventory(UpdateInventoryRequest updateInventoryRequest);
}
