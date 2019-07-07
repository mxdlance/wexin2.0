package com.gdkm.weixin.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.gdkm.weixin.CommonsConfig;

@SpringBootApplication

@EnableJpaRepositories(basePackages = "com.gdkm")
@EntityScan("com.gdkm")
@ComponentScan("com.gdkm")
public class LibraryApplication implements CommonsConfig {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}
}
