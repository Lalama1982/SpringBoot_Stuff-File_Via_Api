package com.vscode.maven.springboot.filesviaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FilesviaapiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FilesviaapiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(FilesviaapiApplication.class);
	}

}
