package com.projectmanagement.controller;

import com.projectmanagement.controlleradvice.OrganizationNotFoundException;
import com.projectmanagement.controlleradvice.ResourceNotFoundException;
import com.projectmanagement.dto.request.CreateProject;
import com.projectmanagement.service.OrganizationService;
import com.projectmanagement.service.ProjectService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/project/")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ProjectService projectService;

    @PreAuthorize("hasAuthority('create:project')")
    @PostMapping("create")
    public ResponseEntity<?> triggerCreateProject(
            @Valid @RequestBody CreateProject createProject,
            @Parameter(
                    name = "orgId",
                    description = "Organization ID",
                    required = true,
                    example = "12345"
            )
            @RequestHeader("orgId") String orgId) {
        return projectService.createProject(orgId, createProject);
    }

    @PreAuthorize("hasAuthority('read:project')")
    @GetMapping("get")
    public ResponseEntity<?> triggerGetProject(
            @Parameter(
                    name = "orgId",
                    required = true
            )
            @RequestHeader("orgId") String orgId,
            @RequestParam(value = "getBy", required = true) String getBy,
            @RequestParam(value = "id", required = false) String id
    ) {
        logger.info("Create Organization Execution Begins");
        if(getBy.equals("organization")){
            return projectService.getProjectByOrganization(orgId);
        }
        else if(getBy.equals("project")){
            return projectService.getProjectByProjectId(id);
        }
        else{
            throw new ResourceNotFoundException("Requested Resource not found");
        }
    }

    @PreAuthorize("hasAuthority('update:project')")
    @PutMapping("update/{id}")
    public ResponseEntity<?> triggerUpdateProject(
            @Valid @RequestBody CreateProject createProject,
            @Parameter(
                    name = "orgId",
                    description = "Organization ID",
                    required = true,
                    example = "12345"
            )
            @RequestHeader("orgId") String orgId,
            @PathVariable("id") String id
    ) {
        logger.info("Create Organization Execution Begins");
        if(organizationService.checkOrganization(orgId)) {
            return projectService.updateProject(createProject, id);
        }
        else{
            throw new OrganizationNotFoundException("Requested Organization not found or deleted");
        }
    }

    @PreAuthorize("hasAuthority('delete:project')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> triggerDeleteProject(
            @Parameter(
                    name = "orgId",
                    description = "Organization ID",
                    required = true,
                    example = "12345"
            )
            @RequestHeader("orgId") String orgId,
            @PathVariable("id") String id
    ) {
        logger.info("Create Organization Execution Begins");
        return projectService.deleteProject(id);
    }
}

