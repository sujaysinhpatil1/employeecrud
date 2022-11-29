package com.microservices.employeecrudop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootTest
class EmployeecrudopApplicationTests {

	@Test
	void contextLoads() {
	}

	// Junit test cases
	// API testing

	// append method written in controller with Test word
	@Test
	public void findAllEmpTest() throws URISyntaxException {

		System.out.println("Test started");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/findAllEmp";   		// url can be created dynamically
		URI uri = new URI(url);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		Assertions.assertEquals(200, response.getStatusCodeValue());  // HTTP status code --> success or fail
		System.out.println(response.getStatusCode());
		System.out.println("Test ended");

	}


}
