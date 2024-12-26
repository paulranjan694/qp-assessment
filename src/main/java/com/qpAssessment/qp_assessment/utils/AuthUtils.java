package com.qpAssessment.qp_assessment.utils;

import com.qpAssessment.qp_assessment.exceptions.ResourceNotFound;
import com.qpAssessment.qp_assessment.model.Role;
import com.qpAssessment.qp_assessment.model.User;
import com.qpAssessment.qp_assessment.repositoryService.UserRepositoryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    private UserRepositoryService userRepositoryService;

    public AuthUtils(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepositoryService.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFound("User Not Found with username: " + authentication.getName()));
        return user;
    }

    public boolean isAdmin(){
       User user = getLoggedInUser();
       if(user.getRole().equalsIgnoreCase(Role.ADMIN.name())) {
           return true;
       }
       return false;
    }
}
