package br.com.bancosimplificado.math.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.bancosimplificado.math.exception.type.DataIntegrityViolationException;
import br.com.bancosimplificado.math.exception.type.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.NOT_FOUND.value(), 
				"Object Not Found",
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> hHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				"Invalid Character",
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				"Data Breach",
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				"Data Breach",
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> constraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
	 
	    StandardError error = new StandardError(
	            System.currentTimeMillis(),
	            HttpStatus.BAD_REQUEST.value(),
	            "Validation Error",
	            "O CPF/CNPJ é inválido!",
	            request.getRequestURI()
	    );
	 
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<StandardError> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.METHOD_NOT_ALLOWED.value(), 
				"Method Not Allowed",
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<StandardError> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), 
				"Unsupported Media Type",
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<StandardError> nullPointerException(NullPointerException ex, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				"Internal Server Error",
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}