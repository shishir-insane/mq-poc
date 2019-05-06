package com.sk.mq.poc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.mq.poc.service.ShipmentConsumerService;
import com.sk.mq.poc.validation.MessageValidationService;

@Service
public class DefaultShipmentConsumerService implements ShipmentConsumerService {
	
	@Autowired
	private MessageValidationService messageParserValidationService;

	@Override
	public void processConsumedMessage(String message) {
		messageParserValidationService.validateReceivedMessage(message);
	}

	

}
