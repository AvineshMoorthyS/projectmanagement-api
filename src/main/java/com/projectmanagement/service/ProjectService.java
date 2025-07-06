package com.projectmanagement.service;

import com.projectmanagement.config.MapperConfig;
import com.projectmanagement.controlleradvice.OrganizationNotFoundException;
import com.projectmanagement.controlleradvice.ProjectNotFoundException;
import com.projectmanagement.controlleradvice.SqlException;
import com.projectmanagement.controlleradvice.UserNotFoundException;
import com.projectmanagement.dto.internal.UserDto;
import com.projectmanagement.dto.request.CreateProject;
import com.projectmanagement.dto.response.CreateProjectResponseDto;
import com.projectmanagement.helper.User;
import com.projectmanagement.model.ProjectModel;
import com.projectmanagement.model.ProjectUserModel;
import com.projectmanagement.repository.ProjectRepository;
import com.projectmanagement.repository.ProjectUserRepository;
import com.projectmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class ProjectService {

    @Autowired
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ProjectUserRepository projectUserRepository;

    @Transactional
    public ResponseEntity<?> createProject(String orgId, CreateProject createProject){
        if(organizationService.checkOrganization(orgId)){
            String username = user.getUserName();
            CreateProjectResponseDto createProjectResponseDto = saveProject(orgId, username, createProject);
            return ResponseEntity.ok(createProjectResponseDto);
        }
        else{
            throw new OrganizationNotFoundException("Requested Organization is Invalid or Deleted");
        }
    }

    private CreateProjectResponseDto saveProject(String orgId, String username, CreateProject createProject){
        ProjectModel projectModel = MapperConfig.convertCreateProjectToProjectModel(createProject);
        projectModel.setOrgId(orgId);
        projectModel.setCreatedBy(username);
        projectModel.setLastModifiedBy(username);
        projectRepository.save(projectModel);
        if(!projectModel.getId().isEmpty()){
            CreateProjectResponseDto createProjectResponseDto = MapperConfig.convertProjectModelToCreateProjectResponseDto(projectModel);
            List<Map<String, String>> users = saveProjectUsers(createProjectResponseDto.getId(), username, createProject.getMembers());
            createProjectResponseDto.setMembers(users);
            return createProjectResponseDto;
        }else{
            throw new SqlException("Error is Saving Project Details");
        }
    }

    private List<Map<String, String>> saveProjectUsers(String projectId, String createdUserName, List<String> users){
        String missingUserList = "";
        List<Map<String, String>> assignedUserList = new ArrayList<>();
        List<String> userIdList = new ArrayList<>();
        for(String username : users){
            UserDto userDto = userRepository.findFirstByUsername(username);
            if(userDto==null){
                missingUserList = missingUserList.isEmpty() ? username : missingUserList + ", " + username;
            }
            else{
                Map<String, String> assignedUser = new HashMap<>();
                assignedUser.put("username", username);
                assignedUser.put("id", userDto.getId());
                assignedUserList.add(assignedUser);
                userIdList.add(userDto.getId());
            }
        }
        if(missingUserList.isEmpty()){
            for(int usersIndex=0; usersIndex < users.size(); usersIndex++){
                ProjectUserModel projectUserModel = new ProjectUserModel();
                projectUserModel.setProjectId(projectId);
                projectUserModel.setUserId(userIdList.get(usersIndex));
                projectUserModel.setCreatedBy(createdUserName);
                projectUserRepository.save(projectUserModel);
            }
            return assignedUserList;
        }
        else{
            throw new UserNotFoundException("The following user(s) are not found : " + missingUserList + ". Create User before assign to Project");
        }
    }

    public ResponseEntity<?> getProjectByOrganization(String orgId){
        ResponseEntity<?> organizationResponse = organizationService.getOrganization(orgId);
        if(organizationResponse.getStatusCode()== HttpStatusCode.valueOf(200)){
            List<ProjectModel> retrieverProjectList = projectRepository.findByOrganizationId(orgId);
            if(!retrieverProjectList.isEmpty()) {
                List<CreateProjectResponseDto> processedProjectList = new ArrayList<>();
                for (ProjectModel projectModel : retrieverProjectList) {
                    CreateProjectResponseDto createProjectResponseDto = MapperConfig.convertProjectModelToCreateProjectResponseDto(projectModel);
                    processedProjectList.add(createProjectResponseDto);
                }
                return ResponseEntity.ok(processedProjectList);
            }
            else{
                throw new ProjectNotFoundException("Request project not found or deleted");
            }
        }else{
            return organizationResponse;
        }
    }

    public ResponseEntity<?> getProjectByProjectId(String projectId){
        ProjectModel projectModel = projectRepository.findFirstById(projectId);
        if(projectModel!=null) {
            CreateProjectResponseDto createProjectResponseDto = MapperConfig.convertProjectModelToCreateProjectResponseDto(projectModel);
            return ResponseEntity.ok(createProjectResponseDto);
        }else{
            throw new ProjectNotFoundException("Request Project not found or deleted");
        }
    }

    public ResponseEntity<?> updateProject(CreateProject createProject, String id){
        String username = user.getUserName();
        List<Map<String, String>> savedUserList = saveProjectUsers(id, username, createProject.getMembers());
        ProjectModel projectModel = MapperConfig.convertCreateProjectToProjectModel(createProject);
        log.info("Inside");
        int updatedRow = projectRepository.updateProject(
                id,
                projectModel.getName(),
                projectModel.getDescription(),
                projectModel.getStatus(),
                projectModel.getEta(),
                projectModel.getPriority());
        log.info("After query executed");
        if(updatedRow>0){
            CreateProjectResponseDto responseDto = MapperConfig.convertProjectModelToCreateProjectResponseDto(projectModel);
            responseDto.setId(id);
            responseDto.setMembers(savedUserList);
            return ResponseEntity.ok(responseDto);
        }else {
            throw new SqlException("Can't able to update the Project properly");
        }
    }

    public ResponseEntity<?> deleteProject(String id){
        int updatedRows = projectUserRepository.deleteProjectUsers(id);
        return ResponseEntity.ok(updatedRows);
    }

}