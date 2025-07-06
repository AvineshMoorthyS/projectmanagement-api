package com.projectmanagement.dto.internal;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ExceptionDto {

    private String message;
    private String error;
    private int statusCode;
    private String errorCode;
    private Instant timestamp;

}