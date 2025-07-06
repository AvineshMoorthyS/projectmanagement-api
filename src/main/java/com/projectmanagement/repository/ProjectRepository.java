package com.projectmanagement.repository;

import com.projectmanagement.model.ProjectModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectModel, String> {

    @Query("SELECT p FROM ProjectModel p WHERE p.orgId = :id AND p.active = 'Y'")
    List<ProjectModel> findByOrganizationId(String id);

    @Query("SELECT p FROM ProjectModel p WHERE p.id = :id AND p.active = 'Y'")
    ProjectModel findFirstById(String id);

    @Modifying
    @Transactional
    @Query("UPDATE ProjectModel p SET p.name = :name, p.description = :description, p.status = :status, p.eta = :eta, p.priority = :priority WHERE p.id = :id AND p.active = 'Y'")
    int updateProject(
            @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("status") String status,
            @Param("eta") String eta,
            @Param("priority") String priority);

}