package com.qpAssessment.qp_assessment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseModel{

    @Column(name = "total_amount")
    private double totalAmount;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order",orphanRemoval = true,cascade = {CascadeType.ALL})
    private List<OrderItem> orderlists = new ArrayList<>();

}
