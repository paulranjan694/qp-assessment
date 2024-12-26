package com.qpAssessment.qp_assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class QpAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(QpAssessmentApplication.class, args);
	}

}
