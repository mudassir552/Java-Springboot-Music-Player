package com.example.demo.FileReader;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Controllers.UserController;
import com.example.demo.Exceptions.NoFilesFoundException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class FileValidator {
	private static final Logger logger = LoggerFactory.getLogger(FileValidator.class);

	public boolean vaildateFile(MultipartFile file) throws IOException {
		
		if(file.isEmpty()|| file==null) {
			throw new NoFilesFoundException("No Files to Validate");
			
		}
		
		return validate(file.getBytes(),file);
		
	}

	public boolean validate(byte[] bytes,MultipartFile file) {
		// TODO Auto-generated method stub
		////int filelength=bytes.length;
		//int mb=1024*1024;
		String fileContent=file.getContentType();
         logger.info("FileContenttttt"+fileContent);
       
		String fileextension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
		logger.info("filelengthh"+fileextension);
		if( fileContent != null && !fileContent.isBlank() && (fileextension.equals("jpeg")||(fileextension.equals("jpg")||fileextension.equals("png")||fileextension.equals("gif")))){ {
               return true;
               
	}
		
}
		return false;
		

}
}