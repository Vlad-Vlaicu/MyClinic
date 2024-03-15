package com.pweb.MyClinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan({"com.pweb.MyClinic.model"})
@EnableJpaRepositories({"com.pweb.MyClinic.repository"})
@EnableJpaAuditing
@EnableConfigurationProperties
@EnableTransactionManagement
public class MyClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyClinicApplication.class, args);
	}
}