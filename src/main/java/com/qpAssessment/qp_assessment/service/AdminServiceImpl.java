package com.qpAssessment.qp_assessment.service;

import com.qpAssessment.qp_assessment.dto.request.GroceryItemRequest;
import com.qpAssessment.qp_assessment.dto.request.UpdateInventoryRequest;
import com.qpAssessment.qp_assessment.dto.response.GetAllGroceryItemsResponse;
import com.qpAssessment.qp_assessment.dto.response.GroceryItemResponse;
import com.qpAssessment.qp_assessment.dto.response.UpdateInventoryResponse;
import com.qpAssessment.qp_assessment.model.GroceryItem;
import com.qpAssessment.qp_assessment.repositoryService.GroceryItemRepositoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AdminServiceImpl implements AdminService {

    private GroceryItemRepositoryService groceryItemRepositoryService;

    public AdminServiceImpl(GroceryItemRepositoryService groceryItemRepositoryService) {
        this.groceryItemRepositoryService = groceryItemRepositoryService;
    }

    @Override
    public GroceryItemResponse addItem(GroceryItemRequest groceryItemRequest) {
        GroceryItem groceryItem = new GroceryItem();
        mapRequestDtoToModel(groceryItemRequest, groceryItem);
        GroceryItem savedGroceryItem = groceryItemRepositoryService.addItem(groceryItem);
        GroceryItemResponse groceryItemResponse = new GroceryItemResponse();
        mapModelToResponseDto(savedGroceryItem, groceryItemResponse);
        return groceryItemResponse;
    }

    
    private void mapModelToResponseDto(GroceryItem savedGroceryItem, GroceryItemResponse groceryItemResponse) {
        groceryItemResponse.setId(savedGroceryItem.getId());
        groceryItemResponse.setItemName(savedGroceryItem.getName());
        groceryItemResponse.setPrice(savedGroceryItem.getPrice());
        groceryItemResponse.setQuantity(savedGroceryItem.getQuantity());
        groceryItemResponse.setCreatedAt(savedGroceryItem.getCreatedAt());
    }

    private void mapRequestDtoToModel(GroceryItemRequest groceryItemRequest, GroceryItem groceryItem) {
        groceryItem.setName(groceryItemRequest.getItemName());
        groceryItem.setPrice(groceryItemRequest.getPrice());
        groceryItem.setQuantity(groceryItemRequest.getQuantity());
    }

    @Override
    public GetAllGroceryItemsResponse getItems(int pageNumber, int pageSize) {
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
        Page<GroceryItem> pageItems = groceryItemRepositoryService.getItems(pageDetails);
        List<GroceryItem> groceryItems = pageItems.getContent();


        List<GroceryItemResponse> groceryItemResponses = new ArrayList<>();

        for(GroceryItem item : groceryItems){
            GroceryItemResponse itemResp = new GroceryItemResponse();
            mapModelToResponseDto(item, itemResp);
            groceryItemResponses.add(itemResp);
        }

        GetAllGroceryItemsResponse getAllGroceryItemsResponse = getGetAllGroceryItemsResponse(groceryItemResponses, pageItems);

        return getAllGroceryItemsResponse;
    }

    @Override
    public GroceryItemResponse updateItem(long itemId, GroceryItemRequest groceryItemRequest) {
        GroceryItem groceryItemFromDB = groceryItemRepositoryService.findItemById(itemId);
        
        if(groceryItemRequest.getItemName() != null && !groceryItemRequest.getItemName().isBlank()){
            groceryItemFromDB.setName(groceryItemRequest.getItemName());
        }

        if(groceryItemRequest.getPrice() != 0L){
            groceryItemFromDB.setPrice(groceryItemRequest.getPrice());
        }

        GroceryItem savedItem = groceryItemRepositoryService.addItem(groceryItemFromDB);
        GroceryItemResponse groceryItemResponse = new GroceryItemResponse();
        mapModelToResponseDto(savedItem, groceryItemResponse);
        return groceryItemResponse;
    }

    @Override
    public void deleteItem(long itemId) {
        GroceryItem groceryItemFromDB = groceryItemRepositoryService.findItemById(itemId);
        groceryItemRepositoryService.deleteItem(groceryItemFromDB);
    }

    @Override
    public GetAllGroceryItemsResponse getItemsInventory(int pageNumber, int pageSize, int minAvailableQuantity) {
        List<GroceryItem> items = groceryItemRepositoryService.getItems();
        items = items.stream().filter(item -> item.getQuantity() <= minAvailableQuantity).toList();

        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), items.size());

        List<GroceryItem> pageContent = items.subList(start, end);
        Page<GroceryItem> pageItems = new PageImpl<>(pageContent, pageRequest, items.size());

        List<GroceryItem> groceryItems = pageItems.getContent();

        List<GroceryItemResponse> groceryItemResponses = new ArrayList<>();

        for(GroceryItem item : groceryItems){
            GroceryItemResponse itemResp = new GroceryItemResponse();
            mapModelToResponseDto(item, itemResp);
            groceryItemResponses.add(itemResp);
        }

        GetAllGroceryItemsResponse getAllGroceryItemsResponse = getGetAllGroceryItemsResponse(groceryItemResponses, pageItems);

        return getAllGroceryItemsResponse;
    }

    @Override
    @Transactional
    public UpdateInventoryResponse updateInventory(UpdateInventoryRequest updateInventoryRequest) {
        Map<Long,Integer> itemsToBeUpdate = updateInventoryRequest.getItems();
        List<GroceryItemResponse> updatedItems = new ArrayList<>();
        for(Map.Entry<Long,Integer> itemToUpdate : itemsToBeUpdate.entrySet()){
            Long itemId = itemToUpdate.getKey();
            int newQuantity = itemToUpdate.getValue();
            GroceryItem item = groceryItemRepositoryService.findItemById(itemId);
            item.setQuantity(newQuantity);
            GroceryItem itemWithUpdatedQuantity = groceryItemRepositoryService.addItem(item);
            GroceryItemResponse groceryItemResponse = new GroceryItemResponse();
            mapModelToResponseDto(itemWithUpdatedQuantity,groceryItemResponse);
            updatedItems.add(groceryItemResponse);
        }

        UpdateInventoryResponse updateInventoryResponse = new UpdateInventoryResponse();
        updateInventoryResponse.setData(updatedItems);
        return updateInventoryResponse;
    }

    private GetAllGroceryItemsResponse getGetAllGroceryItemsResponse(List<GroceryItemResponse> groceryItemResponses, Page<GroceryItem> pageItems) {
        GetAllGroceryItemsResponse getAllGroceryItemsResponse = new GetAllGroceryItemsResponse();
        getAllGroceryItemsResponse.setData(groceryItemResponses);
        getAllGroceryItemsResponse.setPageNumber(pageItems.getNumber());
        getAllGroceryItemsResponse.setPageSize(pageItems.getSize());
        getAllGroceryItemsResponse.setTotalElements(pageItems.getTotalElements());
        getAllGroceryItemsResponse.setTotalPages(pageItems.getTotalPages());
        getAllGroceryItemsResponse.setLastPage(pageItems.isLast());
        return getAllGroceryItemsResponse;
    }


}
