package com.qpAssessment.qp_assessment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInventoryRequest {
    Map<Long,Integer> items = new HashMap<>();
}
