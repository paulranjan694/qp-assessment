package com.qpAssessment.qp_assessment.repositoryService;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qpAssessment.qp_assessment.exceptions.ResourceNotFound;
import com.qpAssessment.qp_assessment.model.GroceryItem;
import com.qpAssessment.qp_assessment.repository.GroceryItemRepository;

@Service
public class GroceryItemRepositoryServiceImpl implements GroceryItemRepositoryService {

    private GroceryItemRepository groceryItemRepository;

    public GroceryItemRepositoryServiceImpl(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    @Override
    public GroceryItem addItem(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }

    @Override
    public Page<GroceryItem> getItems(Pageable pageDetails) {
        return groceryItemRepository.findAll(pageDetails);
    }

    @Override
    public GroceryItem findItemById(long itemId) {
       Optional<GroceryItem> optionalItem = groceryItemRepository.findById(itemId);
       if(!optionalItem.isPresent()){
            throw new ResourceNotFound("item not available with id: "+ itemId);
       }
       GroceryItem groceryItem = optionalItem.get();
       return groceryItem;
    }

    @Override
    public void deleteItem(GroceryItem groceryItemFromDB) {
        groceryItemRepository.delete(groceryItemFromDB);
    }

    @Override
    public List<GroceryItem> getItems() {
        return groceryItemRepository.findAll();
    }

}
