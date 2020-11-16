package com.csis3275;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * @author Hackermen
 * Hotel Management System
 * SpringMVC configuration to resolve views and establish communication between controller model and view
 */


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.csis3275")

//Add to read the database config
@PropertySource("classpath:database.properties")

public class SpringMVCConfig implements WebMvcConfigurer {

	@Autowired
	Environment environment;
	private final String URL = "url";
	private final String USER = "dbuser";
	private final String DRIVER = "driver";
	private final String PASSWORD = "dbpassword";

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty(URL));
		driverManagerDataSource.setUsername(environment.getProperty(USER));
		driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
		driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
		return driverManagerDataSource;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000000);
		return multipartResolver;
	}

	// View, this class resolves the view name to the .jsp file.
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	// Messages to be passed between the controller model and view.
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	// Allow file requests past static to go through to the "/static" folder.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		String path = this.getClass().getClassLoader().getResource("").getPath();
		
		//CREATTING "STATIC/IMAGES" DIRECTORY TO HOLD UPLOADED FILES
		File newDir = new File(path + "/static/images");
		if (!newDir.exists()){
			newDir.mkdirs();
		}
		
		String fullPath;
		try {
			fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("/WEB-INF/classes/");
			fullPath = pathArr[0] + "/static/images/";
		    fullPath = fullPath.replace("/C:", "file://");
			
			registry.addResourceHandler("/images/**").addResourceLocations(fullPath);
		    System.out.println(fullPath);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		registry.addResourceHandler("/static/**").addResourceLocations("/static/");

	}
}