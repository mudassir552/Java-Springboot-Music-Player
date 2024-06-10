package com.example.demo.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Exceptions.InvalidFileException;
import com.example.demo.Exceptions.NoFilesFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.io.File;
@Component
public class FileReader { 
	
    
    @Autowired
    private FileCompressAndDecompress FileCompressAndDecompress;
	
	/*
	 * private String path = "src/main/resources/static/images";
	 * 
	 * public File readLatestFile() { File directory = new File(path); File[] files
	 * = directory.listFiles();
	 * 
	 * if (files != null && files.length > 0) { File latestFile = files[0]; for
	 * (File file : files) { if (file.lastModified() > latestFile.lastModified()) {
	 * latestFile = file; } }
	 * 
	 * System.out.println(latestFile); return latestFile; } else { throw new
	 * NoFilesFoundException("There are no files at the location: " + path); } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public byte[] readFiletoBytes(File f) throws IOException {
	 * 
	 * if(f!=null) {
	 * 
	 * 
	 * try (FileInputStream fileinputstream = new FileInputStream(f)) {
	 * 
	 * return fileinputstream.readAllBytes();
	 * 
	 * }catch(IOException io) {
	 * 
	 * throw new IOException("Unable to read file ");
	 * 
	 * }
	 * 
	 * 
	 * } return null ;
	 * 
	 * }
	 */
 
    
   
        
        public byte[] readFiletoBytes(MultipartFile file) throws IOException  {
        	
           
           
                return file.getBytes();
           
        }

        // Example usage
        public byte[] MultiparttoByte(MultipartFile multipartFile) throws IOException {
        	
        	
        	
        	byte fileBytes[] = null;
        	
        
            try {
            	FileValidator fileValidator = new FileValidator();
            	  if(fileValidator.vaildateFile(multipartFile)) {
            		  
            		 
                 fileBytes = FileCompressAndDecompress.compressFile(multipartFile);
                 
            	  }
            	  
            	  else {
            		  throw new InvalidFileException("File format is invalid");
            	  }
                // Now you can use the byte array 'fileBytes' as needed
            } catch (IOException e) {
                // Handle IOException
                 throw e;
            }
            
        	catch(NoFilesFoundException ex) {
        		throw ex;
        	}
            
            return fileBytes;
        }
    }

    

