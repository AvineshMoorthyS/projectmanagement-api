package com.projectmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserModel {

    @Id
    private String userId;
    private String provider;
    private String email;
    private String username;
    private String userRole;
    private String isUserCreated;
    private String isRoleAssignedToUser;
    private boolean email_verified;
    private char active = 'Y';
    @CreationTimestamp
    private Timestamp updatedAt;

}