package com.helpinghands.admin.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.helpinghands.exception.ResultException;
import com.helpinghands.response.Result;
import com.helpinghands.response.Result.ComplainSystemError;

@ControllerAdvice
public class AdminException extends ResponseEntityExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(AdminException.class);

	@ExceptionHandler({ Exception.class })
	private ResponseEntity<Result<String>> exception(Exception ex) {
		List<ComplainSystemError> error = new ArrayList<>();
		if (ex.getCause() == null) {
			error.add(new ComplainSystemError((ex.getMessage().hashCode()),
					"there is some problem in the inbuilt functionality"));
			logger.error("\n" + ex.hashCode() + "\n" + ex.getCause().getMessage() + "\n");
		} else {
			error.add(new ComplainSystemError((ex.getMessage().hashCode()), ex.getCause().getMessage()));
		}
		logger.error("\n" + ex.hashCode() + "\n" + ex.getMessage());
		Result<String> result = new Result<>(500, "unfortunately! there was some error at the server side.", error);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}
	
	@ExceptionHandler({ DuplicateKeyException.class })
	private ResponseEntity<Result<Object>> duplicateKeyExceptionException(DuplicateKeyException ex) {
		String errorMsg = ex.getCause().getMessage();
		String fieldName = errorMsg.substring(errorMsg.lastIndexOf(" ")+1);
		errorMsg = errorMsg.contains("Duplicate") ? fieldName + " field requires a unique value" : errorMsg;
		logger.error("\n" + ex.hashCode() + " ---- " + ex.getMessage());
		Result<Object> result = new Result<>(406, errorMsg);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@ExceptionHandler({ ResultException.class })
	private ResponseEntity<Result<Object>> resultException(ResultException ex) {
		logger.error("\n" + ex.hashCode() + " ---- " + ex.getMessage());
		Result<Object> result = ex.getResultExecption();
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		logger.error("\n" + ex.hashCode() + " ---- " + ex.getMessage());
		return new ResponseEntity<>(
				new Result<>(400, "Error! unable to parse the given data",
						new ArrayList<>(
								Arrays.asList(new ComplainSystemError(ex.hashCode(), ex.getLocalizedMessage())))),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("\n" + ex.hashCode() + " ---- " + ex.getMessage());
		List<ComplainSystemError> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(new ComplainSystemError(error.hashCode(), error.getDefaultMessage(), error.getField()));
		}
		return new ResponseEntity<>(new Result<>(400, "Missing! Object body missing", errors), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("\n" + ex.hashCode() + " ---- " + ex.getMessage());
		return new ResponseEntity<>(
				new Result<>(400, "Error! unable to read the given data",
						new ArrayList<>(
								Arrays.asList(new ComplainSystemError(ex.hashCode(), ex.getLocalizedMessage())))),
				HttpStatus.BAD_REQUEST);
	}

}