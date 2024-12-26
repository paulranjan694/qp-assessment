package com.qpAssessment.qp_assessment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseModel {
    @NotBlank(message="name cannot be empty")
    private String name;

    @NotBlank(message="username is invalid")
    @Column(unique = true)
    private String username;

    @NotBlank(message="Password is invalid")
    @Size(min = 6, message = "Password length should be more than 6")
    private String password;

    @Email(message="email is invalid")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number")
    @Column(unique = true)
    private String mobile;

    @Column(name="roles")
    private String role = Role.USER.name(); //default role would be 'USER', if required Admin/Super User can grant admin role or privileges to user

    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Order> orders;

}
