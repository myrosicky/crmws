package org.stockws.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.business.exceptions.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Value("${error.map}")
	private Map<String, String> errors;
	
	@ExceptionHandler(AppException.class)
	ResponseEntity<String> handleAppException(AppException e){
		String errMsg = e.getMessage();
		if(StringUtils.isBlank(errMsg)){
			errMsg = errors.get(e.getErrCode());
		}
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(e.getMessage());
	}
}
