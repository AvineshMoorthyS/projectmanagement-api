package com.projectmanagement.dto.request;

import com.projectmanagement.enums.Plan;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrganization {

    @NotNull
    private String name;

    private String industry;

    private String domain;

    private String address;

    @NotNull
    private Plan plan;

}