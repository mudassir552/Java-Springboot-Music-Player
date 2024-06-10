package com.example.demo.FileReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Controllers.UserController;
import com.google.common.primitives.Bytes;

public class FileCompressAndDecompress {
	
	private static final Logger logger = LoggerFactory.getLogger(FileCompressAndDecompress.class);

	public byte [] compressFile(MultipartFile file) throws IOException {
		
		try (InputStream is = file.getInputStream();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				GZIPOutputStream gzipOS = new GZIPOutputStream(bos)){
			
			
	         byte[] buffer = new byte[1024];
			int bytes;
			
			
			while((bytes=is.read(buffer))!=-1) {
				
				 gzipOS.write(buffer, 0, bytes);
			}
			
			gzipOS.finish();
			
			logger.info("BIOS"+bos.toByteArray().length);
			return bos.toByteArray();
			
		}
		catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
			
		
	}
	
	
	public byte[] decompressFile(byte[] compressedData) throws IOException {
	    try (ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
	         GZIPInputStream gzipIS = new GZIPInputStream(bis);
	         ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

	        byte[] buffer = new byte[1024];
	        int bytesRead;

	        while ((bytesRead = gzipIS.read(buffer)) != -1) {
	            baos.write(buffer, 0, bytesRead);
	        }
	        
	        gzipIS.close();

	        return baos.toByteArray();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
	

