package com.example.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("com.example.*.mapper")
@EnableWebMvc
@EnableScheduling // 定时任务启动的注解
public class ImportServiceBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImportServiceBackApplication.class, args);
	}

	@Value("${spring.servlet.multipart.location}")
	private String TEMP_LOCATION;

	@Bean MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
			TEMP_LOCATION = "D:" + TEMP_LOCATION;
		} else {  //linux 和mac

		}
		System.out.println("-----配置临时目录文件路径：" + TEMP_LOCATION);
		factory.setLocation(TEMP_LOCATION);
		return factory.createMultipartConfig();
	}
}
