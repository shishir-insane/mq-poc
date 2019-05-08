/**
 * AppRestConfig.java
 * mq-poc-validation-manager
 * Copyright 2019 Shishir Kumar
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License 
 * version 2 as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU General Public License for more details.
 */
package com.sk.db.poc.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppRestConfig {

	private static final int TIMEOUT_IN_MS = 10000;

	/**
	 * Rest template.
	 *
	 * @return the rest template
	 */
	@Bean
	public RestTemplate restTemplate() {
		final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		restTemplate.getMessageConverters().add(messageConverter);
		return restTemplate;
	}

	/**
	 * Gets the client http request factory.
	 *
	 * @return the client http request factory
	 */
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(TIMEOUT_IN_MS);
		return clientHttpRequestFactory;
	}
}
