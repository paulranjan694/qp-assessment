package com.qpAssessment.qp_assessment.service;

import com.qpAssessment.qp_assessment.dto.request.UserLoginRequest;
import com.qpAssessment.qp_assessment.dto.request.UserRegisterRequest;
import com.qpAssessment.qp_assessment.model.User;

public interface AuthService {
    User registerUser(UserRegisterRequest userRegisterRequest);

    String loginUser(UserLoginRequest userLoginRequest);
}
