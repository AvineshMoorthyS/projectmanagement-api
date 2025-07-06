package com.projectmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "project")
public class ProjectModel {

    @Id
    private String id;

    private String orgId;

    private String name;

    private String description;

    private String eta;

    private char active = 'Y';

    private String status;

    private String priority;

    @CreatedDate
    private LocalDateTime createdAt;

    private String createdBy;

    @LastModifiedDate
    private LocalDateTime lastUpdatedAt;

    private String lastModifiedBy;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

}
