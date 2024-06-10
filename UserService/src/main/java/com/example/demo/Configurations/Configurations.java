package com.example.demo.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import com.example.demo.FileReader.FileCompressAndDecompress;
@Configuration
public class Configurations {

	
	     @Bean
	    public FileCompressAndDecompress FileCompressAndDecompress() {
	        return new FileCompressAndDecompress();
	    }
}
