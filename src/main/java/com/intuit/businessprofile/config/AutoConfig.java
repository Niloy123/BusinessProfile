package com.intuit.businessprofile.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.intuit.businessprofile.client.ProductClient;
import com.intuit.businessprofile.enums.Product;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@EnableMongoRepositories(basePackages = "com.intuit.businessprofile.repository")
public class AutoConfig {

	@Bean(name = "genericClient")
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public Map<Product, ProductClient> getMapForProductClient(List<ProductClient> productClientList) {
		Map<Product, ProductClient> handlerMap = productClientList.stream()
				.collect(Collectors.toMap(productClient -> productClient.getProduct(), productClient -> productClient));
		return Collections.unmodifiableMap(new LinkedHashMap<>(handlerMap));
	}

	@Bean(name = "threadPoolExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(7);
		executor.setMaxPoolSize(42);
		executor.setQueueCapacity(11);
		executor.setThreadNamePrefix("threadPoolExecutor-");
		executor.initialize();
		return executor;
	}
}
