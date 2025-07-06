package com.projectmanagement.controlleradvice;

import com.projectmanagement.dto.internal.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFound(UserNotFoundException ex){
        ExceptionDto exceptionDto = new ExceptionDto();
        Instant timestamp = Instant.now();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setErrorCode("user_not_found_exception");
        exceptionDto.setError("Not Found");
        exceptionDto.setStatusCode(404);
        exceptionDto.setTimestamp(timestamp);
        logger.error(exceptionDto.getMessage());
        logger.warn("Api Execution Ends with Exception");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SqlException.class)
    public ResponseEntity<ExceptionDto> handleSQLError(SqlException ex){
        ExceptionDto exceptionDto = new ExceptionDto();
        Instant timestamp = Instant.now();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setErrorCode("sql_exception");
        exceptionDto.setError("Internal Server Error");
        exceptionDto.setStatusCode(500);
        exceptionDto.setTimestamp(timestamp);
        logger.error(exceptionDto.getMessage());
        logger.warn("Api Execution Ends with Exception");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleOrganizationNotFound(OrganizationNotFoundException ex){
        ExceptionDto exceptionDto = new ExceptionDto();
        Instant timestamp = Instant.now();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setErrorCode("organization_not_found");
        exceptionDto.setError("Not Found");
        exceptionDto.setStatusCode(404);
        exceptionDto.setTimestamp(timestamp);
        logger.error(exceptionDto.getMessage());
        logger.warn("Api Execution Ends with Exception");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception ex){
        ExceptionDto exceptionDto = new ExceptionDto();
        Instant timestamp = Instant.now();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setErrorCode("exception");
        exceptionDto.setError("Internal Server Error");
        exceptionDto.setStatusCode(500);
        exceptionDto.setTimestamp(timestamp);
        ex.printStackTrace();
        logger.error(exceptionDto.getMessage());
        logger.warn("Api Execution Ends with Exception");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResoureNotFoundException(ResourceNotFoundException ex){
        ExceptionDto exceptionDto = new ExceptionDto();
        Instant timestamp = Instant.now();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setErrorCode("resource_not_found");
        exceptionDto.setError("Not Found");
        exceptionDto.setStatusCode(404);
        exceptionDto.setTimestamp(timestamp);
        logger.error(exceptionDto.getMessage());
        logger.warn("Api Execution Ends with Exception");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleProjectNotFoundException(ProjectNotFoundException ex){
        ExceptionDto exceptionDto = new ExceptionDto();
        Instant timestamp = Instant.now();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setErrorCode("project_not_found");
        exceptionDto.setError("Not Found");
        exceptionDto.setStatusCode(404);
        exceptionDto.setTimestamp(timestamp);
        logger.error(exceptionDto.getMessage());
        logger.warn("Api Execution Ends with Exception");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

}
