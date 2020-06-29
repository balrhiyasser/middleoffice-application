package com.example.springsecuritypfe.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class BusinessResourceExceptionDTO {
 
    private String errorCode;
    private String errorMessage;
	private String requestURL;
	private HttpStatus status;
 
}
