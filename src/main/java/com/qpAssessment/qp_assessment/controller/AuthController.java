package com.qpAssessment.qp_assessment.controller;

import com.qpAssessment.qp_assessment.dto.request.UserLoginRequest;
import com.qpAssessment.qp_assessment.dto.request.UserRegisterRequest;
import com.qpAssessment.qp_assessment.dto.response.ApiResponse;
import com.qpAssessment.qp_assessment.dto.response.UserLoginResponse;
import com.qpAssessment.qp_assessment.model.User;
import com.qpAssessment.qp_assessment.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest){
        User user = authService.registerUser(userRegisterRequest);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("User registered successfully. User id -"+ user.getId());
        apiResponse.setSuccess(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        String token = authService.loginUser(userLoginRequest);
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setSuccess(true);
        userLoginResponse.setToken(token);
        return ResponseEntity.ok(userLoginResponse);
    }

}
