package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "math-service")
public interface MathService {

	@PostMapping(value = "/operation")
	Number performOperation(@RequestBody Input input	);
	
	public static class Input{
		
		int number1;
		int number2;
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
		String operation;
		
	}
}
