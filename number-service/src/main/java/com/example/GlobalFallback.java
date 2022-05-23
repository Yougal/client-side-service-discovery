package com.example;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.stereotype.Component;

import com.netflix.discovery.EurekaClient;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import reactor.core.publisher.Mono;

@Component
public class GlobalFallback extends LoadBalancerClientFactory implements RequestInterceptor{

	@Autowired
	private EurekaClient eurekaClient;
	
	
	@Override
	public ReactiveLoadBalancer<ServiceInstance> getInstance(String serviceId) {
		if(eurekaClient.getApplication(serviceId)!=null) {
			return getInstance(serviceId, ReactorServiceInstanceLoadBalancer.class);
		}else {
			return new ReactiveLoadBalancer<ServiceInstance>() {
				
				@Override
				public Publisher<Response<ServiceInstance>> choose(Request request) {
					return Mono.just(new DefaultResponse(new DefaultServiceInstance("0",serviceId,"10.0.0.50",8080,false,null)));
				}
			};
		}
	}


	@Override
	public void apply(RequestTemplate template) {
		if(eurekaClient.getApplication(template.feignTarget().name())==null) {
			template.target("http://10.0.0.50:8080/api/"+template.feignTarget().name());
		}
		System.out.println(template);
	}
	
	//return new RestTemplate().postForObject("http://10.0.0.50:8080/api/math-service/operation", input,Number.class);
	
	
	
}
