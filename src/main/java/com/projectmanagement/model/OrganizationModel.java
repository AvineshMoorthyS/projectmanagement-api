package com.projectmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "organization")
public class OrganizationModel {

    @Id
    private String id;

    @NotNull
    private String name;

    private String industry;

    private String domain;

    private String address;

    @NotNull
    private String plan;

    @CreatedDate
    private LocalDateTime createdAt;

    private String createdBy;

    @LastModifiedDate
    private LocalDateTime lastUpdatedAt;

    private String lastUpdatedBy;

    private char active = 'Y';

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

}