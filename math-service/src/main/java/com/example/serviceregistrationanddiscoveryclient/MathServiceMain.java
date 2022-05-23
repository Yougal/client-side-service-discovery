package com.example.serviceregistrationanddiscoveryclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class MathServiceMain {

		public static void main(String[] args) {
			SpringApplication.run(MathServiceMain.class, args);
		}

	@RestController
	class ServiceInstanceRestController {

		@Autowired
		private DiscoveryClient discoveryClient;

		@RequestMapping("/service-instances/{applicationName}")
		public List<ServiceInstance> serviceInstancesByApplicationName(
				@PathVariable String applicationName) {
			return this.discoveryClient.getInstances(applicationName);
		}
		
		@PostMapping("/operation")
		public Mono<Number> handleOperation(@RequestBody Input input) {
			switch(input.operation) {
				case "-" : return Mono.just(input.number1-input.number2);
				case "+" : return Mono.just(input.number1+input.number2);
				case "*" : return Mono.just(input.number1*input.number2);
				case "/" : return Mono.just(input.number1/input.number2);
			}
			return Mono.error(new RuntimeException("Invalid Operator"));
		}
		
	}

	public static class Input{
		
		public int getNumber1() {
			return number1;
		}
		public void setNumber1(int number1) {
			this.number1 = number1;
		}
		public int getNumber2() {
			return number2;
		}
		public void setNumber2(int number2) {
			this.number2 = number2;
		}
		public String getOperation() {
			return operation;
		}
		public void setOperation(String operation) {
			this.operation = operation;
		}
		int number1;
		int number2;
		String operation;
		
	}
}
