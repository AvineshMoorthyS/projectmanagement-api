package com.projectmanagement.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrganizationResponseDto {

    private String id;
    private String name;
    private String domain;
    private String address;
    private String industry;
    private String plan;
    private String createdBy;

}
