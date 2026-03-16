package com.arctic.backend_for_arctic_team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

@SpringBootApplication
@EnableTransactionManagement
public class BackendForArcticTeamApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendForArcticTeamApplication.class, args);
	}

}
