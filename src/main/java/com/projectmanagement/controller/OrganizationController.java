package com.projectmanagement.controller;

import com.projectmanagement.dto.request.CreateOrganization;
import com.projectmanagement.service.OrganizationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/organization/")
public class OrganizationController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService organizationService;

    @PreAuthorize("hasAuthority('create:organization')")
    @PostMapping("create")
    public ResponseEntity<?> triggerCreateOrganization(
            @Valid @RequestBody CreateOrganization createOrganization
    ) {
        logger.info("Api Execution Begins");
        return organizationService.saveOrganization(createOrganization);
    }

    @PreAuthorize("hasAuthority('read:organization')")
    @GetMapping("get")
    public ResponseEntity<?> triggerGetOrganization(@RequestParam String id) {
        logger.info("Api Execution Begins");
        return organizationService.getOrganization(id);
    }

    @PreAuthorize("hasAuthority('update:organization')")
    @PutMapping("update/{id}")
    public ResponseEntity<?> triggerUpdateOrganization(
            @Valid @RequestBody CreateOrganization createOrganization,
            @PathVariable String id
    ) {
        logger.info("Api Execution Begins");
        return organizationService.updateOrganization(id, createOrganization);
    }

    @PreAuthorize("hasAuthority('delete:organization')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> triggerDeleteOrganization(@PathVariable String id) {
        logger.info("Api Execution Begins");
        return organizationService.deleteOrganization(id);
    }
}
