package br.unicamp.sindo.catalog.error;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(value = NotFoundException.class)
	void handleNotFoundRequests(NotFoundException e, HttpServletResponse response) throws IOException{
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}
	
	@ExceptionHandler(value = BadParameterException.class)
	void handleBadParameter(BadParameterException e, HttpServletResponse response) throws IOException{
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}
	
}
