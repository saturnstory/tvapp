package com.proton.tvapp.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.proton.tvapp.dto.ExceptionResponseDto;
import com.proton.tvapp.dto.TvAppResponse;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers,
																  HttpStatus status, 
																  WebRequest request){
		FieldError fieldError = ex.getBindingResult().getFieldError();
		
		ExceptionResponseDto  expResp = new ExceptionResponseDto();
		expResp.setStatus(status.value());
		expResp.setError(status.toString());
		
		String message = "Field : "+fieldError.getField()+
				 " Code : "+fieldError.getCode()+
				 " "+fieldError.getDefaultMessage();
		expResp.setMessage(message);
		
		return new ResponseEntity<>(new TvAppResponse(expResp), headers, status);
		
		/**
		List<String> errors = ex.getBindingResult()
								.getFieldErrors()
								.stream()
								.map(x -> x.getDefaultMessage())
								.collect(Collectors.toList());
		body.put("errors", errors);
		fieldError.getCode(); //Kısıtlama tipi ismi (Size, NotEmpty, Email) 
		fieldError.getField(); // Doğrulanmayan input field
		**/
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
		String message = ex.getLocalizedMessage();
		
		if(message == null) {
			message = ex.toString();
		}
		
		ExceptionResponseDto expResp = new ExceptionResponseDto();
		expResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		expResp.setMessage(message);
		expResp.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		
		return new ResponseEntity<>(new TvAppResponse(expResp), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
	public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(MethodArgumentTypeMismatchException ex, WebRequest request){		
		String message = ex.getLocalizedMessage();
		
		if(message == null) {
			message = ex.toString();
		}
		
		ExceptionResponseDto expResp = new ExceptionResponseDto();
		expResp.setStatus(HttpStatus.NOT_FOUND.value());
		expResp.setMessage(message);
		expResp.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		
		return new ResponseEntity<>(new TvAppResponse(expResp), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {RecordNotFoundException.class})
	public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request){
		String message = ex.getMessage();
		
		if(message == null) {
			message = ex.toString();
		}
		
		ExceptionResponseDto expResp = new ExceptionResponseDto();
		expResp.setStatus(HttpStatus.NOT_FOUND.value());
		expResp.setMessage(message);
		expResp.setError(HttpStatus.NOT_FOUND.toString());
		
		return new ResponseEntity<>(new TvAppResponse(expResp), new HttpHeaders(), HttpStatus.NOT_FOUND);		
	} 

}
