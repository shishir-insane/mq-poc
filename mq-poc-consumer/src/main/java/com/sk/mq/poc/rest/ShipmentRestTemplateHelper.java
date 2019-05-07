/**
 * ShipmentRestTemplateHelper.java
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
package com.sk.mq.poc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sk.mq.poc.dto.ShipmentMessage;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipmentRestTemplateHelper {

	@Value("${app.config.manager.host-url}")
	private String managerServiceHostUrl;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Post for shipment message.
	 *
	 * @param message      the message
	 * @param headerStatus the header status
	 * @return the string
	 */
	public String postForShipmentMessage(ShipmentMessage message, String headerStatus) {
		final HttpEntity<ShipmentMessage> request = new HttpEntity<ShipmentMessage>(message, getHeaders(headerStatus));
		log.debug("Request to URI: {}", managerServiceHostUrl);
		log.debug("Request: {}", request);
		final String response = restTemplate.postForObject(managerServiceHostUrl, request, String.class);
		log.debug("Response: {}", response);
		return response;
	}

	/**
	 * Gets the headers.
	 *
	 * @param headerStatus the header status
	 * @return the headers
	 */
	private HttpHeaders getHeaders(String headerStatus) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("validation-status", headerStatus);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return headers;
	}
}
