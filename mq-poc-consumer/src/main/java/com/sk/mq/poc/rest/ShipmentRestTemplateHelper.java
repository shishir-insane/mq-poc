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
	
	public String postForShipmentMessage(ShipmentMessage message) {
		HttpEntity<ShipmentMessage> request = new HttpEntity<ShipmentMessage>(message, getHeaders());
		log.debug("Request to URI: {}", managerServiceHostUrl);
		log.debug("Request: {}", request);
		String response = restTemplate.postForObject(managerServiceHostUrl, request, String.class);
		log.debug("Response: {}", response);
		return response;
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return headers;
	}
}
