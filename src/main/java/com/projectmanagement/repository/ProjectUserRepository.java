package com.projectmanagement.repository;

import com.projectmanagement.model.ProjectUserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProjectUserRepository extends JpaRepository<ProjectUserModel, String> {

    @Transactional
    @Modifying
    @Query("UPDATE ProjectUserModel p SET p.active = 'D' WHERE p.id = :id AND p.active = 'Y'")
    int deleteProjectUsers(String id);


}
