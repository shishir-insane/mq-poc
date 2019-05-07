package com.sk.mq.poc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.mq.poc.dto.ShipmentMessage;
import com.sk.mq.poc.rest.ShipmentRestTemplateHelper;
import com.sk.mq.poc.service.ShipmentConsumerService;
import com.sk.mq.poc.validation.impl.ParseMessageValidationService;
import com.sk.mq.poc.validation.impl.SuspiciousConditionValidationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultShipmentConsumerService implements ShipmentConsumerService {
	
	@Autowired
	private ParseMessageValidationService parseMessageValidationService;
	
	@Autowired
	private SuspiciousConditionValidationService suspiciousConditionValidationService;
	
	@Autowired
	private ShipmentRestTemplateHelper restTemplateHelper;

	@Override
	public void processConsumedMessage(String messageText) {
		ShipmentMessage shipmentMessage = parseMessageValidationService.validateReceivedMessage(messageText);
		boolean isInvalidMessage = suspiciousConditionValidationService.isInValidReceivedMessage(shipmentMessage);
		log.info("Is received message invalid? {}", isInvalidMessage);
		String messageResult = restTemplateHelper.postForShipmentMessage(shipmentMessage);
		log.info("Message posted with response: {}", messageResult);
	}

	

}
