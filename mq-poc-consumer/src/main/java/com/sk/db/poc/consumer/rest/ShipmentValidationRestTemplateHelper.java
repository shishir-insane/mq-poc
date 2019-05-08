/**
 * ShipmentValidationRestTemplateHelper.java
 * mq-poc-consumer
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
package com.sk.db.poc.consumer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipmentValidationRestTemplateHelper {

	@Value("${app.config.manager.host-url}")
	private String managerServiceHostUrl;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Post for shipment message.
	 *
	 * @param messageText the message text
	 * @return the string
	 */
	public String postForShipmentMessage(String messageText) {
		final HttpEntity<String> request = new HttpEntity<String>(messageText, getHeaders());
		log.debug("Request to URI: {}", managerServiceHostUrl);
		log.debug("Request: {}", request);
		final String response = restTemplate.postForObject(managerServiceHostUrl, request, String.class);
		log.debug("Response: {}", response);
		return response;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	private HttpHeaders getHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return headers;
	}
}
