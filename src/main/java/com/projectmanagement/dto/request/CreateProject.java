package com.projectmanagement.dto.request;

import com.projectmanagement.enums.Priority;
import com.projectmanagement.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProject {

    @NotNull
    private String name;
    private String description;
    private Status status;
    private Priority priority;
    private List<String> members;
    private String eta;

}