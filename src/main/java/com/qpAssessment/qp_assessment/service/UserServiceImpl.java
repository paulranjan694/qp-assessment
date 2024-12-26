package com.qpAssessment.qp_assessment.service;

import java.time.LocalDateTime;
import java.util.*;

import javax.management.RuntimeErrorException;

import com.qpAssessment.qp_assessment.dto.response.OrderItemResponse;
import com.qpAssessment.qp_assessment.exceptions.BadRequestException;
import com.qpAssessment.qp_assessment.repositoryService.OrderItemRepositoryService;
import com.qpAssessment.qp_assessment.repositoryService.OrderRepositoryService;
import com.qpAssessment.qp_assessment.repositoryService.UserRepositoryService;
import com.qpAssessment.qp_assessment.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qpAssessment.qp_assessment.dto.request.GroceryItemRequest;
import com.qpAssessment.qp_assessment.dto.request.OrderItemRequest;
import com.qpAssessment.qp_assessment.dto.request.OrderRequest;
import com.qpAssessment.qp_assessment.dto.response.GetAllGroceryItemsResponse;
import com.qpAssessment.qp_assessment.dto.response.GroceryItemResponse;
import com.qpAssessment.qp_assessment.dto.response.OrderResponse;
import com.qpAssessment.qp_assessment.exceptions.ResourceNotFound;
import com.qpAssessment.qp_assessment.model.GroceryItem;
import com.qpAssessment.qp_assessment.model.Order;
import com.qpAssessment.qp_assessment.model.OrderItem;
import com.qpAssessment.qp_assessment.model.User;
import com.qpAssessment.qp_assessment.repository.OrderItemRepository;
import com.qpAssessment.qp_assessment.repository.OrderRepository;
import com.qpAssessment.qp_assessment.repository.UserRepository;
import com.qpAssessment.qp_assessment.repositoryService.GroceryItemRepositoryService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    private GroceryItemRepositoryService groceryItemRepositoryService;
    private OrderItemRepositoryService orderItemRepositoryService;
    private OrderRepositoryService orderRepositoryService;
    private UserRepositoryService userRepositoryService;
    private AuthUtils authUtils;

    public UserServiceImpl(GroceryItemRepositoryService groceryItemRepositoryService, OrderItemRepositoryService orderItemRepositoryService,
                           OrderRepositoryService orderRepositoryService, UserRepositoryService userRepositoryService, AuthUtils authUtils) {
        this.groceryItemRepositoryService = groceryItemRepositoryService;
        this.orderItemRepositoryService = orderItemRepositoryService;
        this.orderRepositoryService = orderRepositoryService;
        this.userRepositoryService = userRepositoryService;
        this.authUtils = authUtils;
    }


    @Override
    public GetAllGroceryItemsResponse getItems(int pageNumber, int pageSize) {
        List<GroceryItem> items = groceryItemRepositoryService.getItems();
        items = items.stream().filter(item -> item.getQuantity() > 10).toList();
        
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

        GetAllGroceryItemsResponse getAllGroceryItemsResponse = new GetAllGroceryItemsResponse();
        getAllGroceryItemsResponse.setData(groceryItemResponses);
        getAllGroceryItemsResponse.setPageNumber(pageItems.getNumber());
        getAllGroceryItemsResponse.setPageSize(pageItems.getSize());
        getAllGroceryItemsResponse.setTotalElements(pageItems.getTotalElements());
        getAllGroceryItemsResponse.setTotalPages(pageItems.getTotalPages());
        getAllGroceryItemsResponse.setLastPage(pageItems.isLast());

        return getAllGroceryItemsResponse;
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
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        User user = authUtils.getLoggedInUser();
        List<GroceryItem> itemsInDB = groceryItemRepositoryService.getItems();
        List<OrderItem> orderItemToBePlaced = new ArrayList<>();
        List<GroceryItem> groceryItemsToBeUpdated = new ArrayList<>();
        Map<Long, OrderItemRequest> itemsToBeOrdered = new HashMap<>();

        for(OrderItemRequest orderItem : orderRequest.getOrders()){
            itemsToBeOrdered.put(orderItem.getId(),orderItem);
        }
        double totalPrice = 0.0;
        for(GroceryItem itemInDB : itemsInDB){
            if(itemsToBeOrdered.containsKey(itemInDB.getId())){
                OrderItem orderItem = new OrderItem();
                OrderItemRequest orderItemRequest = itemsToBeOrdered.get(itemInDB.getId());

                int quantityPresentInDB = itemInDB.getQuantity();
                int quantityToBeOrder = orderItemRequest.getQuantity();

                if(quantityToBeOrder > quantityPresentInDB){
                    throw new BadRequestException("item with id: "+ itemInDB.getId()+ " have less quantity in stock");
                }
                totalPrice += (itemInDB.getPrice()*quantityToBeOrder);
                orderItem.setItemName(itemInDB.getName());
                orderItem.setPrice(itemInDB.getPrice());
                orderItem.setQuantity(quantityToBeOrder);
                orderItem.setGroceryItem(itemInDB);

                itemInDB.setQuantity(quantityPresentInDB - quantityToBeOrder);
                groceryItemsToBeUpdated.add(itemInDB);
                OrderItem savedOrderItem =  orderItemRepositoryService.saveOrderItem(orderItem);
                orderItemToBePlaced.add(savedOrderItem);
                itemInDB.getOrderitems().add(savedOrderItem);
                groceryItemRepositoryService.addItem(itemInDB);
                itemsToBeOrdered.remove(itemInDB.getId());
            }
        }

        if(itemsToBeOrdered.size() > 0){
            throw new BadRequestException("Some items in order list are not valid");
        }

        Order order = new Order();
        order.setOrderlists(orderItemToBePlaced);
        order.setTotalAmount(totalPrice);
        order.setUser(user);
        Order savedOrder = orderRepositoryService.saveOrder(order);
        user.getOrders().add(savedOrder);
        userRepositoryService.saveUser(user);
        for(OrderItem orderItem : orderItemToBePlaced){
            orderItem.setOrder(savedOrder);
            orderItemRepositoryService.saveOrderItem(orderItem);
        }

        OrderResponse orderResponse = new OrderResponse();
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        for(OrderItem item : savedOrder.getOrderlists()){
            OrderItemResponse orderItemResponse = new OrderItemResponse();
            orderItemResponse.setItemName(item.getItemName());
            orderItemResponse.setQuantity(item.getQuantity());
            orderItemResponse.setPrice(item.getPrice());
            orderItemResponseList.add(orderItemResponse);
        }
        orderResponse.setOrderId(savedOrder.getId());
        orderResponse.setTotalAmount(savedOrder.getTotalAmount());
        orderResponse.setItems(orderItemResponseList);

        return orderResponse;
    }

}
