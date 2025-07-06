package com.projectmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectmanagement.config.MapperConfig;
import com.projectmanagement.controlleradvice.OrganizationNotFoundException;
import com.projectmanagement.controlleradvice.SqlException;
import com.projectmanagement.dto.request.CreateOrganization;
import com.projectmanagement.dto.response.CreateOrganizationResponseDto;
import com.projectmanagement.helper.User;
import com.projectmanagement.model.OrganizationModel;
import com.projectmanagement.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OrganizationService {

    @Autowired
    private User user;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    public ResponseEntity<?> saveOrganization(CreateOrganization createOrganization){
        OrganizationModel organizationModel = MapperConfig.convertCreateOrganizationToOrganizationModel(createOrganization);
        String userName = user.getUserName();
        organizationModel.setCreatedBy(userName);
        organizationModel.setLastUpdatedBy(userName);
        organizationRepository.save(organizationModel);
        if(!organizationModel.getId().isEmpty()){
            CreateOrganizationResponseDto createOrganizationResponseDto = MapperConfig.convertOrganizationModelToCreateOrganizationResponseDto(organizationModel);
            logger.info("Api Execution Ends Successfully");
            return ResponseEntity.status(201).body(createOrganizationResponseDto);
        }else{
            throw new SqlException("Error in Saving Organization Details");
        }
    }

    @Cacheable(value = "organizationCache", key = "#id", unless = "#result.statusCodeValue == 404")
    public ResponseEntity<?> getOrganization(String id){
        OrganizationModel organizationModel = organizationRepository.findFirstById(id);
        if(organizationModel != null){
            CreateOrganizationResponseDto createOrganizationResponseDto = MapperConfig.convertOrganizationModelToCreateOrganizationResponseDto(organizationModel);
            logger.info("Api Execution Ends Successfully");
            return ResponseEntity.ok(createOrganizationResponseDto);
        }else{
            throw new OrganizationNotFoundException("The requested Organization Not Found or Deleted");
        }
    }

    @CacheEvict(value = "organizationCache", key = "#id")
    public ResponseEntity<?> updateOrganization(String id, CreateOrganization createOrganization){
        OrganizationModel organizationModel = MapperConfig.convertCreateOrganizationToOrganizationModel(createOrganization);
        organizationModel.setId(id);
        organizationModel.setLastUpdatedBy(user.getUserName());
        int rowsUpdated = organizationRepository.updateOrganization(id, organizationModel);
        if(rowsUpdated>0){
            CreateOrganizationResponseDto createOrganizationResponseDto = MapperConfig.convertOrganizationModelToCreateOrganizationResponseDto(organizationModel);
            createOrganizationResponseDto.setCreatedBy(organizationModel.getLastUpdatedBy());
            logger.info("Api Execution Ends Successfully");
            return ResponseEntity.ok(createOrganizationResponseDto);
        }else{
            throw new OrganizationNotFoundException("The requested Organization Not Found or Deleted");
        }
    }

    @CacheEvict(value = "organizationCache", key = "#id")
    public ResponseEntity<?> deleteOrganization(String id){
        OrganizationModel organizationModel = new OrganizationModel();
        organizationModel.setId(id);
        int rowsUpdated = organizationRepository.deleteOrganization(id);
        if(rowsUpdated>0){
            return ResponseEntity.status(204).build();
        }else{
            throw new OrganizationNotFoundException("The requested Organization Not Found or Deleted");
        }
    }

    public boolean checkOrganization(String id){
        OrganizationModel organizationModel = organizationRepository.findFirstById(id);
        if(organizationModel != null){
            return true;
        }else{
            return false;
        }
    }

}