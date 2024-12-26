package com.qpAssessment.qp_assessment.service;

import com.qpAssessment.qp_assessment.dto.request.UserLoginRequest;
import com.qpAssessment.qp_assessment.dto.request.UserRegisterRequest;
import com.qpAssessment.qp_assessment.model.Role;
import com.qpAssessment.qp_assessment.model.User;
import com.qpAssessment.qp_assessment.repositoryService.UserRepositoryService;
import com.qpAssessment.qp_assessment.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private UserRepositoryService userRepositoryService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepositoryService userRepositoryService, AuthenticationManager authenticationManager,
                           BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtils jwtUtils) {
        this.userRepositoryService = userRepositoryService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User registerUser(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setMobile(userRegisterRequest.getMobile());
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));

        User savedUser = userRepositoryService.saveUser(user);
        return savedUser;
    }

    @Override
    public String loginUser(UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(),userLoginRequest.getPassword()));
        String token =jwtUtils.generateToken(userLoginRequest.getUsername());
        return token;
    }
}
