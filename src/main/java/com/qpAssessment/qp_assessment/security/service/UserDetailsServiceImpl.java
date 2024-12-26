package com.qpAssessment.qp_assessment.security.service;

import com.qpAssessment.qp_assessment.exceptions.ResourceNotFound;
import com.qpAssessment.qp_assessment.model.User;
import com.qpAssessment.qp_assessment.repositoryService.UserRepositoryService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepositoryService userRepositoryService;

    public UserDetailsServiceImpl(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepositoryService.findByUsername(username);
        if(!optionalUser.isPresent()){
            throw new ResourceNotFound("user with username: '"+username+"' not found");
        }
        User user = optionalUser.get();

        return new UserPrinciple(user);
    }
}
