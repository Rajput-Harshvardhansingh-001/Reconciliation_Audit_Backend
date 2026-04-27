package com.finance.ReconcileAuditSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.finance.ReconcileAuditSystem.model.Entities")
public class ReconcileAuditSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReconcileAuditSystemApplication.class, args);
	}

}
