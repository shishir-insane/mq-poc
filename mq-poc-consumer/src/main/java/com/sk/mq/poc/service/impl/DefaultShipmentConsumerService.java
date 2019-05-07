/**
 * DefaultShipmentConsumerService.java
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
package com.sk.mq.poc.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${app.config.suspicious.condition.status.valid}")
	private String suspiciousShipmentStatus;

	@Value("${app.config.suspicious.condition.status.invalid}")
	private String unSuspiciousShipmentStatus;

	@Autowired
	private ParseMessageValidationService parseMessageValidationService;

	@Autowired
	private SuspiciousConditionValidationService suspiciousConditionValidationService;

	@Autowired
	private ShipmentRestTemplateHelper restTemplateHelper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sk.mq.poc.service.ShipmentConsumerService#processConsumedMessage(java.
	 * lang.String)
	 */
	@Override
	public void processConsumedMessage(String messageText) {
		final ShipmentMessage shipmentMessage = parseMessageValidationService.validateReceivedMessage(messageText);
		final boolean isInvalidMessage = suspiciousConditionValidationService.isInValidReceivedMessage(shipmentMessage);
		log.info("Is received message invalid? {}", isInvalidMessage);
		final String messageResult = restTemplateHelper.postForShipmentMessage(shipmentMessage,
				isInvalidMessage ? StringUtils.replace(suspiciousShipmentStatus, "{TRN}", shipmentMessage.getTrn())
						: unSuspiciousShipmentStatus);
		log.info("Message posted with response: {}", messageResult);
	}

}
