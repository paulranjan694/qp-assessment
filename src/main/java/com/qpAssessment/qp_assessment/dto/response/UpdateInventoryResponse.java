package com.qpAssessment.qp_assessment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInventoryResponse {
   List<GroceryItemResponse> data;
}
