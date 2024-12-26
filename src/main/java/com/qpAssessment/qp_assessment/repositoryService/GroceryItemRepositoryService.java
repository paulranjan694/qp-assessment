package com.qpAssessment.qp_assessment.repositoryService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.qpAssessment.qp_assessment.model.GroceryItem;

public interface GroceryItemRepositoryService {

    GroceryItem addItem(GroceryItem groceryItem);

    Page<GroceryItem> getItems(Pageable pageDetails);

    GroceryItem findItemById(long itemId);

    void deleteItem(GroceryItem groceryItemFromDB);

    List<GroceryItem> getItems();

}
