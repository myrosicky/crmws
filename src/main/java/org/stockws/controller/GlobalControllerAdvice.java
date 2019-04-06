package org.stockws.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.business.exceptions.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

	
	class CustomError{
		private Map<String, String> map;

		public Map<String, String> getMap() {
			return map;
		}

		public void setMap(Map<String, String> map) {
			this.map = map;
		}
	}
	
	@Bean
	@ConfigurationProperties(prefix = "error")
	CustomError customError(){
		return new CustomError();
	}
	
	
	@ExceptionHandler(AppException.class)
	ResponseEntity<String> handleAppException(AppException e){
		String errMsg = e.getMessage();
		if(StringUtils.isBlank(errMsg)){
			errMsg = customError().getMap().get(e.getErrCode());
		}
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(e.getMessage());
	}
}
