package com.photosharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@ServletComponentScan
public class PhotosharingApplication extends SpringBootServletInitializer {

//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(PhotosharingApplication.class);
//    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(PhotosharingApplication.class, args);
	}

}
