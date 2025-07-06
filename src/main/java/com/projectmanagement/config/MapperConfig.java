package com.projectmanagement.config;

import com.projectmanagement.dto.request.CreateOrganization;
import com.projectmanagement.dto.request.CreateProject;
import com.projectmanagement.dto.response.CreateOrganizationResponseDto;
import com.projectmanagement.dto.response.CreateProjectResponseDto;
import com.projectmanagement.model.OrganizationModel;
import com.projectmanagement.model.ProjectModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class MapperConfig {

    private static ModelMapper modelMapper = new ModelMapper();

    static {
        // Configure strict mapping (optional but recommended)
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static OrganizationModel convertCreateOrganizationToOrganizationModel(CreateOrganization createOrganization) {
        return modelMapper.map(createOrganization, OrganizationModel.class);
    }

    public static CreateOrganizationResponseDto convertOrganizationModelToCreateOrganizationResponseDto(OrganizationModel organizationModel) {
        return modelMapper.map(organizationModel, CreateOrganizationResponseDto.class);
    }

    public static ProjectModel convertCreateProjectToProjectModel(CreateProject createProject){
        return modelMapper.map(createProject, ProjectModel.class);
    }

    public static CreateProjectResponseDto convertProjectModelToCreateProjectResponseDto(ProjectModel projectModel) {
        return modelMapper.map(projectModel, CreateProjectResponseDto.class);
    }

}