package com.qpAssessment.qp_assessment.repositoryService;

import com.qpAssessment.qp_assessment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryService {
    User saveUser(User user);
    Optional<User> findByUserId(long id);

    Optional<User> findByUsername(String username);
}
