package com.qpAssessment.qp_assessment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroceryItem extends BaseModel {

    @NotBlank(message = "Name cannot be empty or null")
    private String name;
    @NotNull(message = " Price can't be null")
    private double price;
    @Min(value = 2, message = "Min quantity should be 2")
    private int quantity;

    @ToString.Exclude
    @OneToMany(mappedBy = "groceryItem", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<OrderItem> orderitems = new ArrayList<>();
}
