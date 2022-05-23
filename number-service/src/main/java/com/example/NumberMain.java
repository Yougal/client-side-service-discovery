package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.MathService.Input;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NumberMain {

		public static void main(String[] args) {
			SpringApplication.run(NumberMain.class, args);
		}

	@RestController
	class ServiceInstanceRestController {

		@Autowired
		private MathService matchService;
		
		@PostMapping("/run")
		public Mono<Number> handleOperation(@RequestBody Input input) {
			return Mono.just(matchService.performOperation(input));
		}
		
	}

}
