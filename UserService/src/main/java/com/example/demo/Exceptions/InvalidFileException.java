package com.example.demo.Exceptions;

public class InvalidFileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidFileException(String mssg) {
		
		super(mssg);
	}

}
