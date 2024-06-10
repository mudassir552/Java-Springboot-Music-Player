package com.example.demo.Exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.demo.Exceptions.InvalidFileException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	 @ExceptionHandler(IOException.class)
	    public ResponseEntity<String> handleIOException(IOException ex) {
	        // Handle IOException
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("IO error occurred: " + ex.getMessage());
	    }
	
	@ExceptionHandler(NoFilesFoundException.class)
	public ResponseEntity<?>handleFileException(NoFilesFoundException ex){
		        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("No File to read: " + ex.getMessage());
		
	}
	
	@ExceptionHandler(InvalidFileException.class)
	public ResponseEntity<?>handleFileException(InvalidFileException ex){
		        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("No File to read: " + ex.getMessage());
		
	}
	
	

}
