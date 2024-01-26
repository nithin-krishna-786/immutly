package com.nithin.immutly.exceptions;

import com.nithin.immutly.model.ErrorResponse;
import com.nithin.immutly.model.FieldError;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalRestExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(ProductNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors().stream().map(error -> {
			FieldError fieldError = new FieldError();
			fieldError.setErrorCode(error.getCode());
			fieldError.setField(error.getField());
			return fieldError;
		}).toList();
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setFieldErrors(fieldErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
