package com.photobucket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.photobucket.exception.NotFoundException;
import com.photobucket.exception.UserNotFoundException;
import com.photobucket.service.PostServiceException;

@ControllerAdvice
public class UserControllerExceptionHandler {
	
	@ExceptionHandler(value = {java.lang.NullPointerException.class})
    public ResponseEntity<?> imgNotPresent(){
		return new ResponseEntity<>("Please Fill Up All Details",HttpStatus.EXPECTATION_FAILED);
    }
//
//	@ExceptionHandler(value= {java.util.NoSuchElementException.class})
//	public ResponseEntity<?> exceptionHandler(){
//		return new ResponseEntity<>("No Records Found !", HttpStatus.EXPECTATION_FAILED);
//	}

	@ExceptionHandler(value= {PostServiceException.class})
	public ResponseEntity<?> customExceptionHandler(PostServiceException postServiceException)
	{
		return new ResponseEntity<String>("Bad Object",HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(value = {org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException.class})
    public ResponseEntity<?> exceptionforsizeHandler(){
		return new ResponseEntity<>("Image Size Is Too Large",HttpStatus.EXPECTATION_FAILED);
    }
	
	@ExceptionHandler(value = {org.springframework.core.convert.ConverterNotFoundException.class})
    public ResponseEntity<?> userPresent(){
		return new ResponseEntity<>("Username & Email Id is Already Present",HttpStatus.EXPECTATION_FAILED);
    }
	
	@ExceptionHandler(value = {org.springframework.web.multipart.MultipartException.class})
    public ResponseEntity<?> selectImageFirst(){
		return new ResponseEntity<>("Please Select Image First",HttpStatus.EXPECTATION_FAILED);
    }
	
//	org.springframework.http.converter.HttpMessageNotReadableException
	@ExceptionHandler(value = {org.springframework.http.converter.HttpMessageNotReadableException.class})
    public ResponseEntity<?> sendBodyFirst(){
		return new ResponseEntity<>("Send Valid Data",HttpStatus.EXPECTATION_FAILED);
    }
	
//	javax.persistence.NonUniqueResultException
	@ExceptionHandler(value = {javax.persistence.NonUniqueResultException.class})
    public ResponseEntity<?> sendUniqueExcep(){
		return new ResponseEntity<>("Already Present",HttpStatus.EXPECTATION_FAILED);
    }
	
	@ExceptionHandler(value = {com.photobucket.exception.UserNotFoundException.class})
    public ResponseEntity<?> userNot(UserNotFoundException obj){
		return new ResponseEntity<>(obj.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }
	
	@ExceptionHandler(value = {com.photobucket.exception.NotFoundException.class})
    public ResponseEntity<?> notFoundException(NotFoundException obj){
		return new ResponseEntity<>(obj.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }
	
	
	
}
