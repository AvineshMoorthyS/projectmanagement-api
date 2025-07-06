package com.projectmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "project_user")
public class ProjectUserModel {

    @Id
    private String projectUserId;
    private String projectId;
    private String userId;
    private char active = 'Y';
    @CreatedDate
    private LocalDateTime createdAt;
    private String createdBy;

    @PrePersist
    public void prePersist() {
        if (projectUserId == null) {
            projectUserId = UUID.randomUUID().toString();
        }
    }

}