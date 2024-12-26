package com.qpAssessment.qp_assessment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qpAssessment.qp_assessment.dto.request.OrderRequest;
import com.qpAssessment.qp_assessment.dto.response.GetAllGroceryItemsResponse;
import com.qpAssessment.qp_assessment.dto.response.OrderResponse;
import com.qpAssessment.qp_assessment.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/users")
public class UserController {

     private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/items")
    public ResponseEntity<GetAllGroceryItemsResponse> getItems(
        @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize
    ){
        GetAllGroceryItemsResponse itemsResp = userService.getItems(pageNumber,pageSize);
        return new ResponseEntity<>(itemsResp,HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
       OrderResponse order = userService.createOrder(orderRequest);
       return new ResponseEntity<>(order,HttpStatus.OK);
    }
    
    
}
