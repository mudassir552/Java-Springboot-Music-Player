package com.example.demo.Exceptions;

public class NoFilesFoundException extends RuntimeException {
 
	
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoFilesFoundException(String mssg) {
		   
		    super(mssg);
	   }
}
