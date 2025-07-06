package com.projectmanagement.dto.response;

import com.projectmanagement.enums.Priority;
import com.projectmanagement.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CreateProjectResponseDto {

    private String id;
    private String name;
    private String description;
    private Status status;
    private Priority priority;
    private List<Map<String, String>> members;
    private String eta;
    private String createdBy;

}
