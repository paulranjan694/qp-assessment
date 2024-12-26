package com.qpAssessment.qp_assessment.controller;

import com.qpAssessment.qp_assessment.dto.request.GroceryItemRequest;
import com.qpAssessment.qp_assessment.dto.request.UpdateInventoryRequest;
import com.qpAssessment.qp_assessment.dto.response.ApiResponse;
import com.qpAssessment.qp_assessment.dto.response.GetAllGroceryItemsResponse;
import com.qpAssessment.qp_assessment.dto.response.GroceryItemResponse;
import com.qpAssessment.qp_assessment.dto.response.UpdateInventoryResponse;
import com.qpAssessment.qp_assessment.exceptions.UnAuthorizedAccessException;
import com.qpAssessment.qp_assessment.service.AdminService;
import com.qpAssessment.qp_assessment.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;
    private AuthUtils authUtils;

    public AdminController(AdminService adminService, AuthUtils authUtils) {
        this.adminService = adminService;
        this.authUtils = authUtils;
    }

    @PostMapping("/items")
    public ResponseEntity<GroceryItemResponse> addItem(@RequestBody GroceryItemRequest groceryItemRequest){
        if(!authUtils.isAdmin()) {
            throw new UnAuthorizedAccessException("Don't have enough access to perform the task");
        }
        GroceryItemResponse groceryItemResponse = adminService.addItem(groceryItemRequest);
        return new ResponseEntity<>(groceryItemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public ResponseEntity<GetAllGroceryItemsResponse> getItems(
        @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize
    ) {
        if(!authUtils.isAdmin()) {
            throw new UnAuthorizedAccessException("Don't have enough access to perform the task");
        }
        GetAllGroceryItemsResponse itemsResp = adminService.getItems(pageNumber,pageSize);
        return new ResponseEntity<>(itemsResp,HttpStatus.OK);
    }

    @PutMapping("items/{itemId}")
    public ResponseEntity<GroceryItemResponse> updateItem(@PathVariable long itemId, @RequestBody GroceryItemRequest groceryItemRequest) {
        if(!authUtils.isAdmin()) {
            throw new UnAuthorizedAccessException("Don't have enough access to perform the task");
        }
        GroceryItemResponse groceryItemResponse = adminService.updateItem(itemId, groceryItemRequest);
        return new ResponseEntity<>(groceryItemResponse, HttpStatus.OK);
    }
    
    @DeleteMapping("items/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable long itemId) {
        if(!authUtils.isAdmin()) {
            throw new UnAuthorizedAccessException("Don't have enough access to perform the task");
        }
        adminService.deleteItem(itemId);
        return new ResponseEntity<>("item deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/inventory")
    public ResponseEntity<GetAllGroceryItemsResponse> getItemsInventory(
            @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "3") int minAvailableQuantity
    ){
        if(!authUtils.isAdmin()) {
            throw new UnAuthorizedAccessException("Don't have enough access to perform the task");
        }
        GetAllGroceryItemsResponse itemsResp = adminService.getItemsInventory(pageNumber,pageSize,minAvailableQuantity);
        return new ResponseEntity<>(itemsResp,HttpStatus.OK);
    }

    @PutMapping("/inventory")
    public ResponseEntity<UpdateInventoryResponse> updateInventory(@RequestBody UpdateInventoryRequest updateInventoryRequest){
        if(!authUtils.isAdmin()) {
            throw new UnAuthorizedAccessException("Don't have enough access to perform the task");
        }
        UpdateInventoryResponse updateInventoryResponse = adminService.updateInventory(updateInventoryRequest);
        return new ResponseEntity<>(updateInventoryResponse,HttpStatus.OK);
    }
}
